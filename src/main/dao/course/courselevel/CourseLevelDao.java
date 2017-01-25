package main.dao.course.courselevel;

import java.util.Set;

import main.model.course.CourseLevel;

public interface CourseLevelDao {

    CourseLevel findCourseLevelByID(String id);

    CourseLevel findCourseLevelByName(String name);

    Set<CourseLevel> findAllCourseLevels();

    void saveCourseLevel(CourseLevel entity);

    void updateCourseLevel(CourseLevel entity);

    void deleteCourseLevel(CourseLevel entity);

}
