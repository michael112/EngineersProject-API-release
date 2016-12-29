package main.json.admin.course.edit;

import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.json.admin.course.CourseActivityJson;

@EqualsAndHashCode
public class EditCourseActivityJson {

    @Valid
    @Getter
    @Setter
    private CourseActivityJson courseActivity;

}
