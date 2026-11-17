package com.s2.LogiTrack_S.A.exception;
import java.time.LocalDateTime;
/**

 {
 "timestamp": 2012...,
 "status":....
 "message":....
 "errorCode":....
 }
 */
public record ErrorResponse
        (LocalDateTime timestamp, int status, String message, String errorCode){
}