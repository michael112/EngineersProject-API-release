package main.service.crud.course.grade;

import java.util.Set;

import main.model.course.Grade;

public interface GradeCrudService {

    Grade findGradeByID(String id);

    Set<Grade> findAllGrades();

    void saveGrade(Grade entity);

    void updateGrade(Grade entity);

    void deleteGrade(Grade entity);

    void deleteGradeByID(String id);
}