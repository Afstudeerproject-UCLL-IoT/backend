package core.usecases.greeter.command;

import core.domain.Message;
import core.interfaces.MessageRepository;

// example of a command, we do some validation and persist it
public class AddMessageCommand {

    public static void execute(Message message, MessageRepository messageRepository){
        // check if valid message
        if(message == null) throw new RuntimeException("bla");

        // persist message
        messageRepository.AddMessage(message);
    }
}
