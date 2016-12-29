package main.json.admin.course.edit;

import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EditCourseMaxStudentsJson {

    @Min(value = 1, message = "course.maxstudents.min")
    @Getter
    @Setter
    private int maxStudents;

}
