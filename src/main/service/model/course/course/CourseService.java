package main.service.model.course.course;

import java.util.Set;

import main.model.course.Course;

public interface CourseService {

    Course findCourseByID(String id);

    Set<Course> findAllCourses();

    void saveCourse(Course entity);

    void updateCourse(Course entity);

    void deleteCourse(Course entity);

    void deleteCourseByID(String id);
}