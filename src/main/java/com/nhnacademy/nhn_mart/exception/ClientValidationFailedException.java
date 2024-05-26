package com.nhnacademy.nhn_mart.exception;

import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

public class ClientValidationFailedException extends RuntimeException {
    public ClientValidationFailedException(BindingResult bindingResult) {
        super(bindingResult.getAllErrors()
                .stream()
                .map(error -> new StringBuilder().append("밸리데이션이름=").append(error.getObjectName())
                        .append(",메시지=").append(error.getDefaultMessage())
                        .append(",오류코드=").append(error.getCode()))
                .collect(Collectors.joining(" | ")));
    }
}