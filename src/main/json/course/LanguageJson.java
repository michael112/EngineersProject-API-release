package main.json.course;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;

@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LanguageJson {

    @Getter
    private String id;

    @Getter
    private String name;

    public LanguageJson(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
