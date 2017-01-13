package main.service.controller.admin.type;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import main.util.locale.LocaleCodeProvider;

import main.service.controller.AbstractService;

import main.service.crud.course.coursetype.CourseTypeCrudService;
import main.service.crud.language.LanguageCrudService;

import main.error.exception.IllegalRemovalEntityException;

import main.json.admin.type.view.CourseTypeListJson;

import main.json.admin.type.CourseTypeNameJson;

import main.json.admin.type.CourseTypeJson;

import main.model.course.CourseType;
import main.model.course.CourseTypeName;

@Service("adminTypeService")
public class AdminTypeServiceImpl extends AbstractService implements AdminTypeService {

    private LanguageCrudService languageCrudService;

    private CourseTypeCrudService courseTypeCrudService;

    public CourseTypeListJson getCourseTypeList() {
        try {
            Set<CourseType> courseTypes = this.courseTypeCrudService.findAllCourseTypes();
            CourseTypeListJson result = new CourseTypeListJson();
            for( CourseType courseType : courseTypes ) {
                result.addType(courseType.getId(), courseType.getCourseTypeName(this.localeCodeProvider.getLocaleCode()));
            }
            return result;
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void addCourseType(CourseTypeJson courseTypeJson) {
        try {
            CourseType newCourseType = new CourseType();
            for( CourseTypeNameJson courseTypeNameJson : courseTypeJson.getNamesInLanguages() ) {
                newCourseType.addCourseTypeName(new CourseTypeName(newCourseType, this.languageCrudService.findLanguageByID(courseTypeNameJson.getLanguage()), courseTypeNameJson.getName()));
            }
            this.courseTypeCrudService.saveCourseType(newCourseType);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public main.json.admin.type.view.multilang.CourseTypeJson getCourseTypeInfo(CourseType courseType) {
        try {
            main.json.admin.type.view.multilang.CourseTypeJson result = new main.json.admin.type.view.multilang.CourseTypeJson(courseType.getId());
            for( CourseTypeName courseTypeName : courseType.getCourseTypeNames() ) {
                result.addName(courseTypeName.getNamingLanguage().getId(), courseTypeName.getCourseTypeName());
            }
            return result;
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editCourseTypeNames(CourseType courseType, CourseTypeJson courseTypeJson) {
        try {
            // erasing course type name list
            for( CourseTypeName courseTypeName : courseType.getCourseTypeNames() ) {
                courseType.removeCourseTypeName(courseTypeName);
            }
            for( CourseTypeNameJson courseTypeNameJson : courseTypeJson.getNamesInLanguages() ) {
                courseType.addCourseTypeName(new CourseTypeName(courseType, this.languageCrudService.findLanguageByID(courseTypeNameJson.getLanguage()), courseTypeNameJson.getName()));
            }
            this.courseTypeCrudService.updateCourseType(courseType);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editSingleCourseTypeName(CourseType courseType, CourseTypeNameJson courseTypeNameJson) {
        try {
            for( CourseTypeName courseTypeName : courseType.getCourseTypeNames() ) {
                if( courseTypeName.getNamingLanguage().getId().equals(courseTypeNameJson.getLanguage()) ) {
                    courseTypeName.setCourseTypeName(courseTypeNameJson.getName());
                    this.courseTypeCrudService.updateCourseType(courseType);
                }
            }
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void addCourseTypeName(CourseType courseType, CourseTypeNameJson courseTypeNameJson) {
        try {
            courseType.addCourseTypeName(new CourseTypeName(courseType, this.languageCrudService.findLanguageByID(courseTypeNameJson.getLanguage()), courseTypeNameJson.getName()));
            this.courseTypeCrudService.updateCourseType(courseType);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void removeCourseType(CourseType courseType) {
        try {
            if( courseType.hasActiveCourses() ) {
                throw new IllegalRemovalEntityException();
            }
            else {
                this.courseTypeCrudService.deleteCourseType(courseType);
            }
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    @Autowired
    public AdminTypeServiceImpl(LocaleCodeProvider localeCodeProvider, CourseTypeCrudService courseTypeCrudService, LanguageCrudService languageCrudService) {
        super(localeCodeProvider);
        this.languageCrudService = languageCrudService;
        this.courseTypeCrudService = courseTypeCrudService;
    }

}
