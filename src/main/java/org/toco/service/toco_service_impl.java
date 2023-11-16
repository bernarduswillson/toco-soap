package org.toco.service;

import org.toco.model.*;
import org.toco.entity.*;


import javax.jws.WebService;
import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.xml.ws.developer.JAXWSProperties;
import java.net.InetAddress;
import java.net.InetSocketAddress;

@WebService(endpointInterface = "org.toco.service.toco_service")
public class toco_service_impl implements toco_service {

    @Resource
    WebServiceContext wsctx;

    @Override
    public String addGems(Integer user_id, Integer gem, String type) {
        if (validateApiKey()){
            userGems_Entity userGems = new userGems_Entity(user_id, gem);
            userGems_model userGemsModel = new userGems_model();
            transaction_model transactionModel = new transaction_model();
            transaction_entity tan = new transaction_entity(user_id, gem, type, "ACCEPTED", "0");
            if (userGemsModel.checkUser(user_id)) {
                Integer currentGems = userGemsModel.getUserGems(user_id);
                userGems.setGem(currentGems + gem);
                userGemsModel.update(userGems);
                addLoggging("User with id " + user_id + " added " + gem + " gems");
                transactionModel.insert(tan);
                return "success";
            } else {
                userGemsModel.insert(userGems);
                addLoggging("User with id " + user_id + " added " + gem + " gems");
                transactionModel.insert(tan);
                return "success";
            }
        }
        else {
            addLoggging("User with id " + user_id + " tried to add " + gem + " gems but failed because of invalid api key");
            return "failed";
        }
    }

    @Override
    public Integer getGems(Integer user_id) {
        if(validateApiKey()){
            userGems_model userGemsModel = new userGems_model();
            Integer gems = userGemsModel.getUserGems(user_id);
            addLoggging("User with id " + user_id + " requested his gems");
            return gems;
        }
        else {
            addLoggging("User with id " + user_id + " tried to get his gems but failed because of invalid api key");
            return -1;
        }
    }

    @Override
    public String createTransaction(Integer user_id, Integer amount, String type, String email) {
        if (validateApiKey()){
            userGems_model userGemsModel = new userGems_model();
            Integer userGems = userGemsModel.getUserGems(user_id);
            if (userGems >= amount) {
                userGemsModel.update(new userGems_Entity(user_id, userGems - amount));
                transaction_model transactionModel = new transaction_model();
                transactionModel.insert(new transaction_entity(user_id, amount, type, "accepted", "0"));
                addLoggging("User with id " + user_id + " created a transaction with amount " + amount + " and status ACCEPTED");
                mail.sendMail(email,"Your transaction with " + amount + " gems has been accepted and will be processed immediately.");
                return "success";
            } else {
                transaction_model transactionModel = new transaction_model();
                transactionModel.insert(new transaction_entity(user_id, amount, type, "rejected", "0"));
                addLoggging("User with id " + user_id + " created a transaction with amount " + amount + " and status REJECTED");
                return "insufficient gems";
            }
        }
        else {
            addLoggging("User with id " + user_id + " tried to create a transaction with amount " + amount + " but failed because of invalid api key");
            return "failed";
        }
    }

    @Override
    public String[] getTransactions(Integer user_id) {
        if(validateApiKey()){
            transaction_model transactionModel = new transaction_model();
            transaction_entity[] transactions = transactionModel.getTransaction(user_id);
//            create transactions to a string with each transaction on a new line
            String[] ret = new String[transactionModel.getTransactionCount(user_id)];
            int len = transactionModel.getTransactionCount(user_id);
            for (int i = 0; i < len; i++) {
                ret[i] =transactions[i].getAmount().toString()+", "+transactions[i].getImage()+", "+transactions[i].getStatus()+", "+transactions[i].getCreated_at();
            }
            addLoggging("User with id " + user_id + " requested his transactions");
            return ret;
        }
        else {
            addLoggging("invalid api key for user"+user_id);
            return null;
        }
    }

    @Override
    public String useVoucher(String voucher, Integer user_id, Integer amount) {
        if(validateApiKey()){
            voucher_model voucherModel = new voucher_model();
            userGems_model userGemsModel = new userGems_model();
            transaction_model transactionModel = new transaction_model();
            voucher_entity voucherEntity = new voucher_entity(voucher,user_id,amount,"0");
            voucherModel.insert(voucherEntity);
            transactionModel.insert(new transaction_entity(user_id, amount, "Voucher Redeemed", "accepted", "0"));
            if (userGemsModel.checkUser(user_id)) {
                Integer currentGems = userGemsModel.getUserGems(user_id);
                userGemsModel.update(new userGems_Entity(user_id, currentGems + amount));
            } else {
                userGemsModel.insert(new userGems_Entity(user_id, amount));
            }
            addLoggging("User with id " + user_id + " used voucher " + voucher + "with amount " + amount);
            return "success";
        }
        else {
            addLoggging("User with id " + user_id + " tried to use a voucher with amount " + amount + " but failed because of invalid api key");
            return "invalid apikey";
        }
    }

    @Override
    public String[] getSpecifiedVouchers (String code){
        if(validateApiKey()){
            voucher_model voucherModel = new voucher_model();
            voucher_entity[] voucherEntity = voucherModel.getSpecifiedVoucher(code);
            Integer len = voucherModel.getSpecifiedCount(code);
            String[] ret = new String[len];
            for (int i = 0; i < len; i++) {
                ret[i] = voucherEntity[i].getCode()+", "+voucherEntity[i].getAmount().toString()+", "+voucherEntity[i].getUser_id().toString()+", "+voucherEntity[i].getCreated_at();
            }
            return ret;
        }
        else {
            addLoggging("invalid api key");
            return null;
        }
    }

    @Override
    public String[] getAllVouchers() {
        if(validateApiKey()){
            voucher_model voucherModel = new voucher_model();
            voucher_entity[] voucherEntity = voucherModel.getAllVouchers();
            Integer len = voucherModel.getAllCount();
            String[] ret = new String[len];
            for (int i = 0; i < len; i++) {
                ret[i] = voucherEntity[i].getCode()+", "+voucherEntity[i].getAmount().toString()+", "+voucherEntity[i].getUser_id().toString()+", "+voucherEntity[i].getCreated_at();
            }
            return ret;
        }
        else {
            addLoggging("invalid api key");
            return null;
        }
    }

    public Boolean validateApiKey() {
        api_model apiModel = new api_model();
        MessageContext mc = wsctx.getMessageContext();
        HttpExchange exchange = (HttpExchange) mc.get("com.sun.xml.ws.http.exchange");
        String apiKey = exchange.getRequestHeaders().getFirst("X-API-KEY");
        if (apiKey == null) {
            return false;
        } else if (apiModel.checkApiKey(apiKey)) {
            return true;
        } else {
            return false;
        }
    }

    public void  addLoggging(String description) {
        MessageContext mctx = wsctx.getMessageContext();
        HttpExchange req = (HttpExchange) mctx.get(JAXWSProperties.HTTP_EXCHANGE);
        InetSocketAddress remote = req.getRemoteAddress();
        InetAddress address = remote.getAddress();
        String ip = address.getHostAddress();
        String endpoint = req.getRequestURI().toString();
        logging logging_service = new logging();
        logging_service.addLogging(description, ip, endpoint);
    }
}
