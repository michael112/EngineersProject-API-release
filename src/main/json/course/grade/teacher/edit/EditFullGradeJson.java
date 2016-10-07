package main.json.course.grade.teacher.edit;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EditFullGradeJson {
    @Getter
    @Setter
    private String gradeID;

    @Getter
    @Setter
    private String gradeTitle;

    @Getter
    @Setter
    private String gradeDescription;

    @Getter
    @Setter
    private String scale;

    @Getter
    @Setter
    private Double maxPoints;

    @Getter
    @Setter
    private Double weight;
}
