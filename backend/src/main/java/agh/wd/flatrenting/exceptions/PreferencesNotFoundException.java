package agh.wd.flatrenting.exceptions;

import javax.persistence.EntityNotFoundException;

public class PreferencesNotFoundException extends EntityNotFoundException {
    public PreferencesNotFoundException(String nick) {
        super("Preferences for user " + nick + " not found.");
    }
}
