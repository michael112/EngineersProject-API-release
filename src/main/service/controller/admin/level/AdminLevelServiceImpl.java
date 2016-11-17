package main.service.controller.admin.level;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import main.util.locale.LocaleCodeProvider;

import main.service.controller.AbstractService;

import main.service.crud.course.courselevel.CourseLevelCrudService;

import main.json.admin.level.CourseLevelListJson;
import main.json.admin.level.CourseLevelJson;

import main.model.course.CourseLevel;

@Service("adminLevelService")
public class AdminLevelServiceImpl extends AbstractService implements AdminLevelService {

    private CourseLevelCrudService courseLevelCrudService;

    public CourseLevelListJson getCourseLevelList() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void addCourseLevel(CourseLevelJson newLevel) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public CourseLevelJson getCourseLevelInfo(CourseLevel level) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void swapCourseLevel(CourseLevel level1, CourseLevel level2) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void editCourseLevel(CourseLevel level, CourseLevelJson editedLevel) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public void removeCourseLevel(CourseLevel level) {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    @Autowired
    public AdminLevelServiceImpl(LocaleCodeProvider localeCodeProvider, CourseLevelCrudService courseLevelCrudService) {
        super(localeCodeProvider);
        this.courseLevelCrudService = courseLevelCrudService;
    }

}
