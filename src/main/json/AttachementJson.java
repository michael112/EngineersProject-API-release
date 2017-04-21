package main.json;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;

@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachementJson {

    @Getter
    private String id;

    @Getter
    private String name;

    @Getter
    private String date;

    @Getter
    private String path;

    public AttachementJson(String id, String name, String date, String path) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.path = path;
    }

}
