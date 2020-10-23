package agh.wd.flatrenting.exceptions;

public class ConversationExistsException extends RuntimeException {
    public ConversationExistsException() {
        super("Conversation already exists.");
    }
}
