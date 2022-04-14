package edu.eci.arsw.digitalqueue.exception;

public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(Long id) {
        super("Could not find employee " + id);
    }

    public UserNotFoundException(String name) {
        super("Could not find employee " + name);
    }

}
