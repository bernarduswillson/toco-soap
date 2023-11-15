package org.toco.service;

import org.toco.entity.*;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.ParameterStyle;


@WebService
@SOAPBinding(style = Style.DOCUMENT, parameterStyle = ParameterStyle.WRAPPED)
public interface toco_service {
    @WebMethod
    public String addGems(
            @WebParam(name = "user_id") Integer user_id,
            @WebParam(name = "gem") Integer gem
    );

    @WebMethod
    public Integer getGems(
            @WebParam(name = "user_id") Integer user_id
    );

    @WebMethod
    public String createTransaction(
            @WebParam(name = "user_id") Integer user_id,
            @WebParam(name = "amount") Integer amount,
            @WebParam(name = "type") String type, //add gems / buy item
            @WebParam(name = "email") String email
    );

    @WebMethod
    public String[] getTransactions(
            @WebParam(name = "user_id") Integer user_id
    );

}
