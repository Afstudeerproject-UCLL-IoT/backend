package infrastructure.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MessageEntity {

    @Id
    public int id;

    public String message;

    public MessageEntity() {

    }

    public MessageEntity(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
