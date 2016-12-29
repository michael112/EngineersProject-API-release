package main.json.admin.course.edit;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.json.admin.course.CourseDayJson;

@EqualsAndHashCode
public class EditCourseDaysJson {

    @Valid
    @Getter
    @Setter
    private Set<CourseDayJson> courseDays;

    public void addCourseDay(CourseDayJson courseDayJson) {
        this.courseDays.add(courseDayJson);
    }

    public EditCourseDaysJson() {
        super();
        this.courseDays = new HashSet<>();
    }

}
