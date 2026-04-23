package com.wms.core.domain.exception;

public class BusinessRuleException extends BaseException{

    public BusinessRuleException(String errorCode, String message){
        super(message, errorCode);
    }

    public BusinessRuleException(String errorCode, String messageTemplate, Object... args){
        super(String.format(messageTemplate, args), errorCode);
    }
}
