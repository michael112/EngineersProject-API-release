package main.json.course.grade.teacher.edit;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EditPointsJson {
    @Getter
    @Setter
    private Double maxPoints;

    @NotBlank(message = "grade.weight.empty")
    @Getter
    @Setter
    private Double weight;
}
