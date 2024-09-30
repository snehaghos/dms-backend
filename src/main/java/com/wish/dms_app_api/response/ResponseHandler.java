package com.wish.dms_app_api.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public static String SUCCESS_MESSAGE="success";
    public static String FAILURE_MESSAGE="failure";
    public static int SUCCESS_CODE=1;
    public static int FAILURE_CODE=0;


    public static ResponseEntity<Object> response(int code, Object objectResponse, String message){

        Map<String, Object> map= new HashMap<>();
        map.put("code", code);
        map.put("message", message);
        map.put("data", objectResponse);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    public static ResponseEntity<Object> response(int code, String message){
        Map<String, Object> map= new HashMap<>();
        map.put("code", code);
        map.put("message", message);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
}
