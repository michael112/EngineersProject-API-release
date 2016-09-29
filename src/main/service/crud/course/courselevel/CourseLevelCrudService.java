package main.service.crud.course.courselevel;

import java.util.Set;

import main.model.course.CourseLevel;

public interface CourseLevelCrudService {

    CourseLevel findCourseLevelByID(String id);

    Set<CourseLevel> findAllCourseLevels();

    void saveCourseLevel(CourseLevel entity);

    void updateCourseLevel(CourseLevel entity);

    void deleteCourseLevel(CourseLevel entity);

    void deleteCourseLevelByID(String id);
}