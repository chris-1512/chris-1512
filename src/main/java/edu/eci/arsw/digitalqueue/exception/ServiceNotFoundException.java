package edu.eci.arsw.digitalqueue.exception;

public class ServiceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ServiceNotFoundException(String name) {
        super("Could not find service " + name);
    }

}
