package main.json.course;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NextLessonJson {

    @Getter
    private String day;

    @Getter
    private String hour;

    public NextLessonJson(String day, String hour) {
        this.day = day;
        this.hour = hour;
    }
}
