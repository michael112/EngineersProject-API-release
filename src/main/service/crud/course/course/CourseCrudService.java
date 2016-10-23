package main.service.crud.course.course;

import java.util.Set;

import main.model.course.Course;

public interface CourseCrudService {

    Course findCourseByID(String id);

    Set<Course> findCoursesByQuery(String queryStr);

    Set<Course> findAllCourses();

    void saveCourse(Course entity);

    void updateCourse(Course entity);

    void deleteCourse(Course entity);

    void deleteCourseByID(String id);
}