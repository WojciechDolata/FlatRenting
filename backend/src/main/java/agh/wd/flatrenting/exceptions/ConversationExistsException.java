package agh.wd.flatrenting.exceptions;

public class ConversationExistsException extends Exception {
    public ConversationExistsException() {
        super("Conversation already exists.");
    }
}
