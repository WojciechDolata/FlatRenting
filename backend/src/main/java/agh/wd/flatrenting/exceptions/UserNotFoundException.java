package agh.wd.flatrenting.exceptions;

import javax.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(String nick) {
        super("User " + nick + " not found.");
    }
}
