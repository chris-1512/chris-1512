package edu.eci.arsw.digitalqueue.exception;

public class TurnNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TurnNotFoundException(String id) {
        super("Could not find turn " + id);
    }
}
