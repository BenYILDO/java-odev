package com.beykent.realestate.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleNotFound(IllegalArgumentException ex, Model model) {
        model.addAttribute("mesaj", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception ex, Model model) {
        model.addAttribute("mesaj", "Beklenmeyen bir hata oluştu.");
        return "error/500";
    }
}
