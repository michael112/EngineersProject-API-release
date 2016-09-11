package main.json.course;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageJson {

    @Getter
    private String messageID;

    @Getter
    private String date;

    @Getter
    private String title;

}
