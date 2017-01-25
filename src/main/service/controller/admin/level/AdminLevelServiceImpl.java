package main.service.controller.admin.level;

import java.util.Set;

import java.util.List;
import java.util.ArrayList;

import java.util.Collections;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import main.util.locale.LocaleCodeProvider;

import main.service.controller.AbstractService;

import main.service.crud.course.courselevel.CourseLevelCrudService;

import main.error.exception.IllegalRemovalEntityException;

import main.json.admin.level.CourseLevelListJson;
import main.json.admin.level.CourseLevelJson;

import main.model.course.CourseLevel;

@Service("adminLevelService")
public class AdminLevelServiceImpl extends AbstractService implements AdminLevelService {

    private CourseLevelCrudService courseLevelCrudService;

    public CourseLevelListJson getCourseLevelList() {
        try {
            List<CourseLevel> courseLevels = new ArrayList<>(this.courseLevelCrudService.findAllCourseLevels());
            Collections.sort(courseLevels);
            CourseLevelListJson result = new CourseLevelListJson();
            for( CourseLevel level : courseLevels ) {
                CourseLevelJson courseLevelJson = new CourseLevelJson(level.getName());
                result.addLevel(courseLevelJson);
            }
            return result;
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void addCourseLevel(CourseLevelJson newLevel) {
        try {
            CourseLevel courseLevel = new CourseLevel(newLevel.getName());
            this.courseLevelCrudService.saveCourseLevel(courseLevel);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public CourseLevelJson getCourseLevelInfo(CourseLevel level) {
        try {
            return new CourseLevelJson(level.getName());
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void swapCourseLevel(CourseLevel level1, CourseLevel level2) {
        try {
            String tmpName = level1.getName();
            level1.setName(level2.getName());
            level2.setName(tmpName);
            this.courseLevelCrudService.updateCourseLevel(level1);
            this.courseLevelCrudService.updateCourseLevel(level2);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editCourseLevel(CourseLevel level, CourseLevelJson editedLevel) {
        try {
            level.setName(editedLevel.getName());
            this.courseLevelCrudService.updateCourseLevel(level);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void removeCourseLevel(CourseLevel level) {
        try {
            if( level.hasActiveCourses() ) {
                throw new IllegalRemovalEntityException();
            }
            else {
                this.courseLevelCrudService.deleteCourseLevel(level);
            }
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    @Autowired
    public AdminLevelServiceImpl(LocaleCodeProvider localeCodeProvider, CourseLevelCrudService courseLevelCrudService) {
        super(localeCodeProvider);
        this.courseLevelCrudService = courseLevelCrudService;
    }

}
