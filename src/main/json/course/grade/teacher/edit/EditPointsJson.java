package main.json.course.grade.teacher.edit;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EditPointsJson {
    @Getter
    @Setter
    private String gradeID;

    @Getter
    @Setter
    private Double maxPoints;

    @Getter
    @Setter
    private Double weight;
}
