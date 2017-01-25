package main.service.controller.admin.level;

import main.json.admin.level.view.CourseLevelListJson;

import main.json.admin.level.CourseLevelJson;

import main.model.course.CourseLevel;

public interface AdminLevelService {

    CourseLevelListJson getCourseLevelList();

    void addCourseLevel(CourseLevelJson newLevel);

    main.json.admin.level.view.CourseLevelJson getCourseLevelInfo(CourseLevel level);

    void editCourseLevel(CourseLevel level, CourseLevelJson editedLevel);

    void removeCourseLevel(CourseLevel level);

}
