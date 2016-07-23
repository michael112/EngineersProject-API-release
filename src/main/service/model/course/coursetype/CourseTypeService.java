package main.service.model.course.coursetype;

import java.util.List;

import main.model.course.CourseType;

public interface CourseTypeService {

    CourseType findCourseTypeByID(String id);

    List<CourseType> findAllCourseTypes();

    void saveCourseType(CourseType entity);

    void updateCourseType(CourseType entity);

    void deleteCourseType(CourseType entity);

    void deleteCourseTypeByID(String id);
}