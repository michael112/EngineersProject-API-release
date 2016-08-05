package main.service.model.course.courselevel;

import java.util.Set;

import main.model.course.CourseLevel;

public interface CourseLevelService {

    CourseLevel findCourseLevelByID(String id);

    Set<CourseLevel> findAllCourseLevels();

    void saveCourseLevel(CourseLevel entity);

    void updateCourseLevel(CourseLevel entity);

    void deleteCourseLevel(CourseLevel entity);

    void deleteCourseLevelByID(String id);
}