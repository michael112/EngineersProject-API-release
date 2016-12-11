package main.json.course.message;

import java.util.Set;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class MessageListJson {

    @Getter
    private Set<MessageJson> messages;

}
