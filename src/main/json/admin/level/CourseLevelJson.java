package main.json.admin.level;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CourseLevelJson implements Comparable<CourseLevelJson> {

    @Setter
    @Getter
    private String name;

    public CourseLevelJson() {
        super();
    }

    public CourseLevelJson(String name) {
        this();
        this.setName(name);
    }

    @Override
    public int compareTo(CourseLevelJson other) {
        return this.getName().compareTo(other.getName());
    }

}
