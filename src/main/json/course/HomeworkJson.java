package main.json.course;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class HomeworkJson {

    @Getter
    private String homeworkID;

    @Getter
    private String date;

    @Getter
    private String title;

}
