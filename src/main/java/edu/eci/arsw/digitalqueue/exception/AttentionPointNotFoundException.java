package edu.eci.arsw.digitalqueue.exception;

public class AttentionPointNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AttentionPointNotFoundException(Long id) {
        super("Could not find attention point " + id);
    }

}
