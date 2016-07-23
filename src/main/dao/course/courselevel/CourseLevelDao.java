package main.dao.course.courselevel;

import java.util.List;

import main.model.course.CourseLevel;

public interface CourseLevelDao {

    CourseLevel findCourseLevelByID(String id);

    List<CourseLevel> findAllCourseLevels();

    void saveCourseLevel(CourseLevel entity);

    void updateCourseLevel(CourseLevel entity);

    void deleteCourseLevel(CourseLevel entity);

}
