package main.dao.course.coursetype;

import java.util.Set;

import main.model.course.CourseType;

public interface CourseTypeDao {

    CourseType findCourseTypeByID(String id);

    Set<CourseType> findAllCourseTypes();

    void saveCourseType(CourseType entity);

    void updateCourseType(CourseType entity);

    void deleteCourseType(CourseType entity);

}
