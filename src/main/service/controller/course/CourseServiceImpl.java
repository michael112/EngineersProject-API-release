package main.service.controller.course;

import org.springframework.stereotype.Service;

import main.model.course.Course;
import main.model.course.CourseMembership;

import main.model.user.User;

import main.json.course.CourseListJson;
import main.json.course.CourseUserJson;

@Service("courseService")
public class CourseServiceImpl implements CourseService {

    public CourseListJson getCourseStudentList(Course course, String currentLocale) {
        if( course == null ) throw new IllegalArgumentException();
        String languageName = course.getLanguage().getLanguageName(currentLocale);
        String courseTypeName = course.getCourseType().getCourseTypeName(currentLocale);
        CourseListJson result = new CourseListJson(course.getId(), languageName, course.getCourseLevel().getName(), course.getCourseType().getId(), courseTypeName);
        for( CourseMembership studentMembership : course.getStudents() ) {
            User student = studentMembership.getUser();
            result.addStudent(new CourseUserJson(student.getId(), student.getFullName()));
        }
        for( User teacher : course.getTeachers() ) {
            result.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
        }
        return result;
    }

}
