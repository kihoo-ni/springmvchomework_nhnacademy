package com.nhnacademy.student_info.controller;


import com.nhnacademy.student_info.exception.ValidationFailedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WebControllerAdvice {

    @ExceptionHandler(ValidationFailedException.class)
    public String handleValidationFailedException(ValidationFailedException ex, Model model) {
        model.addAttribute("validationErrors", ex);
        return "error";
    }



    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("error", ex);
        return "error";
    }



}
