package org.toco.service;

import org.toco.model.*;
import org.toco.entity.*;


import javax.jws.WebService;
import javax.jws.WebMethod;
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
    public String addGems(Integer user_id, Integer gem) {
        if (validateApiKey()){
            userGems_Entity userGems = new userGems_Entity(user_id, gem);
            userGems_model userGemsModel = new userGems_model();
            if (userGemsModel.checkUser(user_id)) {
                Integer currentGems = userGemsModel.getUserGems(user_id);
                userGems.setGem(currentGems + gem);
                userGemsModel.update(userGems);
                addLoggging("User with id " + user_id + " added " + gem + " gems");
                return "success";
            } else {
                userGemsModel.insert(userGems);
                addLoggging("User with id " + user_id + " added " + gem + " gems");
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
    public String createTransaction(Integer user_id, Integer amount, String type) {
        if (validateApiKey()){
            userGems_model userGemsModel = new userGems_model();
            Integer userGems = userGemsModel.getUserGems(user_id);
            if (userGems >= amount) {
                userGemsModel.update(new userGems_Entity(user_id, userGems - amount));
                transaction_model transactionModel = new transaction_model();
                transactionModel.insert(new transaction_entity(user_id, amount, type, "accepted"));
                addLoggging("User with id " + user_id + " created a transaction with amount " + amount + " and description ACCEPTED");
                return "success";
            } else {
                transaction_model transactionModel = new transaction_model();
                transactionModel.insert(new transaction_entity(user_id, amount, type, "rejected"));
                addLoggging("User with id " + user_id + " created a transaction with amount " + amount + " and description REJECTED");
                return "insufficient gems";
            }
        }
        else {
            addLoggging("User with id " + user_id + " tried to create a transaction with amount " + amount + " but failed because of invalid api key");
            return "failed";
        }
    }

    @Override
    public transaction_entity[] getTransactions(Integer user_id) {
        if(validateApiKey()){
            transaction_model transactionModel = new transaction_model();
            transaction_entity[] transactions = transactionModel.getTransaction(user_id);
            addLoggging("User with id " + user_id + " requested his transactions");
            return transactions;
        }
        else {
            addLoggging("invalid api key for user"+user_id);
            return null;
        }
    }

    public Boolean validateApiKey() {
        String[] API_KEYS = { "toco_rest", "Postman", "toco_php"};
        MessageContext mc = wsctx.getMessageContext();
        HttpExchange exchange = (HttpExchange) mc.get("com.sun.xml.ws.http.exchange");
        String apiKey = exchange.getRequestHeaders().getFirst("X-API-KEY");
        if (apiKey == null) {
            return false;
        } else if (apiKey.equals(API_KEYS[0]) || apiKey.equals(API_KEYS[1]) || apiKey.equals(API_KEYS[2])) {
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
