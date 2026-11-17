package com.s2.LogiTrack_S.A.exception;



public class BusinessRuleException extends RuntimeException{
    public BusinessRuleException(String mensaje){
        super(mensaje);
    }
}