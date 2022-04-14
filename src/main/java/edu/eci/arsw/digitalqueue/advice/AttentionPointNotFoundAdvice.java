package edu.eci.arsw.digitalqueue.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import edu.eci.arsw.digitalqueue.exception.AttentionPointNotFoundException;

/**
 * AttentionPointNotFoundAdvice
 */
@ControllerAdvice
public class AttentionPointNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(AttentionPointNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)

    String attentionPointNotFoundHandler(AttentionPointNotFoundException exception){
        return exception.getMessage();
    }

}