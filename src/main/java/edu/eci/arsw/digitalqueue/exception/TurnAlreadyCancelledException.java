package edu.eci.arsw.digitalqueue.exception;

public class TurnAlreadyCancelledException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TurnAlreadyCancelledException(String code) {
        super("Turn already cancelled " + code);
    }

}
