package com.ing.brokercore.utils.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class ExceptionResponse {

    private String error;
    private String message;
    private int status;

}
