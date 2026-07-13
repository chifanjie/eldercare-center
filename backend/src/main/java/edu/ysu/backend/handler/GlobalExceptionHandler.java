package edu.ysu.backend.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public void exceptionHandler(Exception exception, HttpServletResponse response){
        log.error("Operation event recorded");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

}
