package main.service.model.course.course;

import java.util.List;

import main.model.course.Course;

public interface CourseService {

    Course findCourseByID(String id);

    List<Course> findAllCourses();

    void saveCourse(Course entity);

    void updateCourse(Course entity);

    void deleteCourse(Course entity);

    void deleteCourseByID(String id);
}