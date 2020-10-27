package agh.wd.flatrenting.exceptions;

public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException() {
        super("You are not authorized to do this.");
    }
}
