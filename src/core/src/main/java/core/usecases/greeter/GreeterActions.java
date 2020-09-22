package core.usecases.greeter;

import core.domain.Message;
import core.interfaces.MessageRepository;
import core.usecases.greeter.command.AddMessageCommand;
import core.usecases.greeter.query.GetAllMessagesQuery;

import java.util.List;

// facade for all the greeter actions (command and queries), very thin logic is in the command and queriess
public class GreeterActions {

    private final MessageRepository messageRepository;

    public GreeterActions(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void sendMessage(Message message){
        AddMessageCommand.execute(message,messageRepository);
    }

    public List<Message> RetrieveMessages(){
        return GetAllMessagesQuery.execute(messageRepository);
    }
}
