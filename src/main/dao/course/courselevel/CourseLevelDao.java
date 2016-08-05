package main.dao.course.courselevel;

import java.util.Set;

import main.model.course.CourseLevel;

public interface CourseLevelDao {

    CourseLevel findCourseLevelByID(String id);

    Set<CourseLevel> findAllCourseLevels();

    void saveCourseLevel(CourseLevel entity);

    void updateCourseLevel(CourseLevel entity);

    void deleteCourseLevel(CourseLevel entity);

}
