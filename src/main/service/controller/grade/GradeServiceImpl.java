package main.service.controller.grade;

import org.springframework.stereotype.Service;

import main.model.course.Course;
import main.model.user.User;

import main.json.course.grade.student.GradeListJson;

@Service("gradeService")
public class GradeServiceImpl implements IGradeService {

    public GradeListJson getStudentGradeList(User student, Course course) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

}
