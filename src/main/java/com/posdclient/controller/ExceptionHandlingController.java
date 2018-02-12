package com.posdclient.controller;

import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by mneata on 8/18/2016.
 */
@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler({JSONException.class})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void jsonException() {

    }
}
