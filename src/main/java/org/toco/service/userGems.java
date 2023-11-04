package org.toco.service;

import org.toco.model.*;
import org.toco.entity.*;


import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.xml.internal.ws.developer.JAXWSProperties;
import java.net.InetAddress;
import java.net.InetSocketAddress;




@WebService
public class userGems {

    @Resource
    WebServiceContext wsctx;
    @WebMethod
    public void addGems(Integer user_id, Integer gem) {
        userGems_Entity userGems = new userGems_Entity(user_id, gem);
        userGems_model userGemsModel = new userGems_model();
//        check if the user already exists add the gem to his gems if user doesnt exist create new user
        if(userGemsModel.checkUser(user_id)){
            Integer currentGems = userGemsModel.getUserGems(user_id);
            userGems.setGem(currentGems + gem);
            userGemsModel.update(userGems);
            addLoggging("User with id " + user_id + " added " + gem + " gems");
        }
        else{
            userGemsModel.insert(userGems);
            addLoggging("User with id " + user_id + " added " + gem + " gems");
        }
    }

    @WebMethod
    public Integer getGems(Integer user_id) {
        userGems_model userGemsModel = new userGems_model();
        Integer gems = userGemsModel.getUserGems(user_id);
        addLoggging("User with id " + user_id + " requested his gems");
        return gems;
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
