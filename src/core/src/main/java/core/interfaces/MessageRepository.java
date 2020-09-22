package core.interfaces;

import core.domain.Message;

import java.util.List;

public interface MessageRepository {
    public void AddMessage(Message message);
    public List<Message> GetAllMessages();
}
