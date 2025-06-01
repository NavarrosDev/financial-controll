package com.navarro.financial.controll.account.infra;

import com.navarro.financial.controll.account.exceptions.AccountAlreadyExists;
import com.navarro.financial.controll.account.exceptions.AccountNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class AccountControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AccountNotFoundException.class)
    public ProblemDetail entityNotFoundExceptionHandler(AccountNotFoundException ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Account Not found exception, check the documentation");
        problemDetail.setProperty("timeStamp", LocalDateTime.now());
        return problemDetail;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AccountAlreadyExists.class)
    public ProblemDetail AccountAlreadyExistsHandler(AccountAlreadyExists ex, HttpServletRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Account already exists exception, check the documentation");
        problemDetail.setProperty("timeStamp", LocalDateTime.now());
        return problemDetail;
    }
}
