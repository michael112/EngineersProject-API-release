package main.json.response;

import org.springframework.http.HttpStatus;

import main.json.course.message.MessageListJson;

import lombok.Getter;

public class MessageListResponseJson extends AbstractResponseJson {

    @Getter
    private MessageListJson messages;

    public MessageListResponseJson(MessageListJson messages, String message, HttpStatus status) {
        super(message, status);
        this.messages = messages;
    }

}
