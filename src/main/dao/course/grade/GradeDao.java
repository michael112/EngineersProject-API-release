package main.dao.course.grade;

import java.util.List;

import main.model.course.Grade;

public interface GradeDao {

    Grade findGradeByID(String id);

    List<Grade> findAllGrades();

    void saveGrade(Grade entity);

    void updateGrade(Grade entity);

    void deleteGrade(Grade entity);

}
