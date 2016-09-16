package main.json.course;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageJson {

    @Getter
    private String messageID;

    @Getter
    private String title;

    public MessageJson(String messageID, String title) {
        this.messageID = messageID;
        this.title = title;
    }

}
