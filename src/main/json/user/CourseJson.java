package main.json.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseJson {

    @Getter
    private String id;
    @Getter
    private String language;
    @Getter
    private String level;
    @Getter
    private Boolean confirmed;


    public CourseJson(String id, String language, String level) {
        this.id = id;
        this.language = language;
        this.level = level;
        this.confirmed = null;
    }
    public CourseJson(String id, String language, String level, boolean confirmed) {
        this(id, language, level);
        this.confirmed = confirmed;
    }
}
