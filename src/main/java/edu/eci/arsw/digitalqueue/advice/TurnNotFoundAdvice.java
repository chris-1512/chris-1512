package edu.eci.arsw.digitalqueue.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import edu.eci.arsw.digitalqueue.exception.TurnNotFoundException;

/**
 * TurnNotFoundAdvice
 */
@ControllerAdvice
public class TurnNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(TurnNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String turnNotFoundHandler(TurnNotFoundException exception) {
        return exception.getMessage();
    }

}