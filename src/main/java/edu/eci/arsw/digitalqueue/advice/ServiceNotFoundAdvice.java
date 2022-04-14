package edu.eci.arsw.digitalqueue.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import edu.eci.arsw.digitalqueue.exception.ServiceNotFoundException;

/**
 * QueueNotFoundAdvice
 */
@ControllerAdvice
public class ServiceNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ServiceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)

    String queueNotFoundHandler(ServiceNotFoundException exception){
        return exception.getMessage();
    }

}