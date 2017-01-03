package main.json.course.grade.teacher.edit;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EditPointsJson {
    @Getter
    @Setter
    private Double maxPoints;

    @NotNull(message = "grade.weight.empty")
    @Getter
    @Setter
    private Double weight;

    public EditPointsJson() {
        super();
    }

    public EditPointsJson(Double maxPoints, Double weight) {
        this();
        this.setMaxPoints(maxPoints);
        this.setWeight(weight);
    }
}
