package agh.wd.flatrenting.exceptions;

import javax.persistence.EntityNotFoundException;

public class ConversationNotFoundException extends EntityNotFoundException {
    public ConversationNotFoundException(String id) {
        super("Conversation " + id + " not found.");
    }
}
