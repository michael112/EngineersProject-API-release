package main.json.course.grade.teacher.edit;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;

@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentGradeJson {
    @Getter
    @Setter
    private String studentID;

    @Getter
    @Setter
    private String gradeID;

    @Getter
    @Setter
    private double grade;
}
