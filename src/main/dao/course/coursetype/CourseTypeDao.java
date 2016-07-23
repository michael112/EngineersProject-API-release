package main.dao.course.coursetype;

import java.util.List;

import main.model.course.CourseType;

public interface CourseTypeDao {

    CourseType findCourseTypeByID(String id);

    List<CourseType> findAllCourseTypes();

    void saveCourseType(CourseType entity);

    void updateCourseType(CourseType entity);

    void deleteCourseType(CourseType entity);

}
