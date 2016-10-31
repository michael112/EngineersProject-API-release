package main.json.course.homework;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class HomeworkGradeJson {

    @Getter
    private String scale;

    @Getter
    private double weight;

    @Getter
    private double grade;

    @Getter
    private Double maxPoints;

    public HomeworkGradeJson(String scale, double grade, double weight, Double maxPoints) {
        this.scale = scale;
        this.grade = grade;
        this.weight = weight;
        this.maxPoints = maxPoints;
    }

}
