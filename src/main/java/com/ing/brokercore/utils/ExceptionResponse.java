package com.ing.brokercore.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor
public class ExceptionResponse {

    private String error;
    private String message;
    private int status;

}
