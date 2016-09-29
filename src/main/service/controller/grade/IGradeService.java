package main.service.controller.grade;

import main.json.course.grade.student.GradeListJson;

import main.model.user.User;
import main.model.course.Course;

public interface IGradeService {

    GradeListJson getStudentGradeList(User student, Course course);

}
