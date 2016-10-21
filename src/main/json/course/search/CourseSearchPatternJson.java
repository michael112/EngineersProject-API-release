package main.json.course.search;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CourseSearchPatternJson {

    @Getter
    @Setter
    private String language;

    @Getter
    @Setter
    private String courseType;

}
