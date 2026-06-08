package com.beykent.realestate.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (statusCode == null) return "error/error";

        int code = Integer.parseInt(statusCode.toString());
        if (code == HttpStatus.NOT_FOUND.value())           return "error/404";
        if (code == HttpStatus.INTERNAL_SERVER_ERROR.value()) return "error/500";
        if (code == HttpStatus.FORBIDDEN.value())           return "error/403";
        return "error/error";
    }
}
