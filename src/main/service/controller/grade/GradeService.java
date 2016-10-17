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

    void createNewGrade(NewGradeJson newGradeJson, Course course, User currentUser);

    void editGrade(EditFullGradeJson editGradeJson, Grade gradeToEdit);

    void editGrade(EditGradeInfoJson editGradeJson, Grade gradeToEdit);

    void editGrade(EditPointsJson editGradeJson, Grade gradeToEdit);

    void editGrade(EditScaleJson editGradeJson, Grade gradeToEdit);

    void createStudentGrade(StudentGradeJson studentGradeJson, Grade gradeToEdit);

    void editStudentGrade(StudentGradeJson editStudentGradeJson, Grade gradeToEdit);

}
