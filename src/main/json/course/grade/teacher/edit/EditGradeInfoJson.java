package main.json.course.grade.teacher.edit;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EditGradeInfoJson {
    @Getter
    @Setter
    private String gradeID;

    @Getter
    @Setter
    private String gradeTitle;

    @Getter
    @Setter
    private String gradeDescription;
}
