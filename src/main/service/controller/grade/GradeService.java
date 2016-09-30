package main.service.controller.grade;

import main.model.user.User;
import main.model.course.Course;

public interface GradeService {

    main.json.course.grade.student.allgrades.list.GradeListJson getStudentGradeList(User student, Course course, String currentLocale);

    main.json.course.grade.teacher.allgrades.list.GradeListJson getTeacherGradeList(Course course, String currentLocale);

}
