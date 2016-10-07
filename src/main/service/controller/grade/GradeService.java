package main.service.controller.grade;

import main.model.user.User;
import main.model.course.Course;
import main.model.course.Grade;

import main.json.course.grade.commons.GradeJson;

import main.json.course.grade.commons.NewGradeJson;

import main.json.course.grade.teacher.edit.EditFullGradeJson;
import main.json.course.grade.teacher.edit.EditGradeInfoJson;
import main.json.course.grade.teacher.edit.EditScaleJson;
import main.json.course.grade.teacher.edit.EditPointsJson;
import main.json.course.grade.teacher.edit.StudentGradeJson;

public interface GradeService {

    main.json.course.grade.student.allgrades.list.GradeListJson getStudentGradeList(User student, Course course);

    main.json.course.grade.teacher.allgrades.list.GradeListJson getTeacherGradeList(Course course);

    GradeJson getGradeInfo(Grade grade);

    void createNewGrade(NewGradeJson newGradeJson);

    void editGrade(EditFullGradeJson editGradeJson);

    void editGrade(EditGradeInfoJson editGradeJson);

    void editGrade(EditPointsJson editGradeJson);

    void editGrade(EditScaleJson editGradeJson);

    void createStudentGrade(StudentGradeJson studentGradeJson);

    void editStudentGrade(StudentGradeJson editStudentGradeJson);

}
