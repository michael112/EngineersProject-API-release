package main.service.controller.admin.level;

import main.json.admin.level.CourseLevelListJson;
import main.json.admin.level.CourseLevelJson;

import main.model.course.CourseLevel;

public interface AdminLevelService {

    CourseLevelListJson getCourseLevelList();

    void addCourseLevel(CourseLevelJson newLevel);

    CourseLevelJson getCourseLevelInfo(CourseLevel level);

    void swapCourseLevel(CourseLevel level1, CourseLevel level2);

    void editCourseLevel(CourseLevel level, CourseLevelJson editedLevel);

    void removeCourseLevel(CourseLevel level);

}
