package main.service.model.course.coursetype;

import java.util.Set;

import main.model.course.CourseType;

public interface CourseTypeService {

    CourseType findCourseTypeByID(String id);

    Set<CourseType> findAllCourseTypes();

    void saveCourseType(CourseType entity);

    void updateCourseType(CourseType entity);

    void deleteCourseType(CourseType entity);

    void deleteCourseTypeByID(String id);
}