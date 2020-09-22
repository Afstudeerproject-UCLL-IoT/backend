package core.usecases.greeter.query;

import core.domain.Message;
import core.interfaces.MessageRepository;

import java.util.List;

// example of a query, we just retrieve all queries
public class GetAllMessagesQuery {

    public static List<Message> execute(MessageRepository messageRepository){
        return messageRepository.GetAllMessages();
    }
}
