package main.json.course.message;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class MessageListJson {

    @Getter
    private Set<AbstractMessageJson> messages;

    public void addMessage(AbstractMessageJson message) {
        this.messages.add(message);
    }

    public MessageListJson() {
        super();
        this.messages = new HashSet<>();
    }

}
