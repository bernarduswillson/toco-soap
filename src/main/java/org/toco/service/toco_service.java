package org.toco.service;

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
            @WebParam(name = "gem") Integer gem,
            @WebParam(name = "type") String type);

    @WebMethod
    public Integer getGems(
            @WebParam(name = "user_id") Integer user_id);

    @WebMethod
    public String createTransaction(
            @WebParam(name = "user_id") Integer user_id,
            @WebParam(name = "amount") Integer amount,
            @WebParam(name = "type") String type, // add gems / buy item
            @WebParam(name = "email") String email);

    @WebMethod
    public String[] getTransactions(
            @WebParam(name = "user_id") Integer user_id);

    @WebMethod
    public String useVoucher(
            @WebParam(name = "code") String code,
            @WebParam(name = "user_id") Integer user_id,
            @WebParam(name = "amount") Integer amount,
            @WebParam(name = "type") String type);

    @WebMethod
    public String[] getSpecifiedVouchers(
            @WebParam(name = "code") String code);

    @WebMethod
    public String[] getAllVouchers(

    );

}
