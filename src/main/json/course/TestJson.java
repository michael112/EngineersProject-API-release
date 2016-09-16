package main.json.course;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestJson {

    @Getter
    private String taskID;

    @Getter
    private String date;

    @Getter
    private String title;

    public TestJson(String taskID, String date, String title) {
        this.taskID = taskID;
        this.date = date;
        this.title = title;
    }
}
