package infrastructure.persistence.implementations;

import core.domain.Message;
import core.interfaces.MessageRepository;
import infrastructure.persistence.entities.MessageEntity;
import infrastructure.persistence.repositories.MessageRepositoryJpa;

import java.util.List;
import java.util.stream.Collectors;

public class MessageRepositoryImpl implements MessageRepository {
    private final MessageRepositoryJpa messageRepositoryJpa;

    public MessageRepositoryImpl(MessageRepositoryJpa messageRepositoryJpa){
        this.messageRepositoryJpa = messageRepositoryJpa;

        AddMessage(new Message(1,"Hello World!"));
        AddMessage(new Message(2,"Nani???"));
        AddMessage(new Message(3,"OOooooof zwaar"));
    }

    @Override
    public void AddMessage(Message message) {
        messageRepositoryJpa.saveAndFlush(new MessageEntity(message.getId(), message.getMessage()));
    }

    @Override
    public List<Message> GetAllMessages() {
        return messageRepositoryJpa.findAll()
                .stream()
                .map(entity -> new Message(entity.id,entity.message))
                .collect(Collectors.toList());
    }
}
