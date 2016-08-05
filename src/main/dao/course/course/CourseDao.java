package main.dao.course.course;

import java.util.Set;

import main.model.course.Course;

public interface CourseDao {

    Course findCourseByID(String id);

    Set<Course> findAllCourses();

    void saveCourse(Course entity);

    void updateCourse(Course entity);

    void deleteCourse(Course entity);

}
