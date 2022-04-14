package edu.eci.arsw.digitalqueue.exception;

public class NoTurnsInServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NoTurnsInServiceException(String name) {
        super("Could not find turns in service " + name);
    }
}
