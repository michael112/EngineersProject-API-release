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

    public HomeworkGradeJson(String scale, double grade) {
        this.scale = scale;
        this.grade = grade;
        this.weight = 1;
    }

    public HomeworkGradeJson(String scale, double grade, double weight) {
        this(scale, grade);
        this.weight = weight;
    }

    public HomeworkGradeJson(String scale, double grade, Double maxPoints) {
        this(scale, grade);
        this.maxPoints = maxPoints;
    }

    public HomeworkGradeJson(String scale, double grade, double weight, Double maxPoints) {
        this(scale, grade);
        this.weight = weight;
        this.maxPoints = maxPoints;
    }

}
