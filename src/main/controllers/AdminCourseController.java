package main.controllers;

import java.util.Set;

import javax.persistence.NoResultException;

import javax.annotation.security.RolesAllowed;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.constants.urlconstants.AdminCourseControllerUrlConstants;

import main.util.labels.LabelProvider;

import main.error.exception.HttpNotFoundException;
import main.error.exception.HttpIllegalAccessException;

import main.error.exception.IllegalRemovalEntityException;

import main.error.exception.IllegalServiceOperationException;

import main.service.crud.course.course.CourseCrudService;
import main.service.crud.user.user.UserCrudService;

import main.service.search.SearchService;

import main.service.controller.admin.course.AdminCourseService;
import main.service.controller.admin.language.AdminLanguageService;
import main.service.controller.admin.type.AdminTypeService;
import main.service.controller.admin.level.AdminLevelService;

import main.json.response.AdminCourseListResponseJson;
import main.json.response.AdminCourseInfoResponseJson;
import main.json.response.AdminCreatingCourseDataResponseJson;
import main.json.response.CourseSearchResultsResponseJson;
import main.json.response.DefaultResponseJson;
import main.json.response.AbstractResponseJson;

import main.json.admin.course.view.CourseListJson;
import main.json.admin.course.view.CourseInfoJson;

import main.json.admin.course.NewCourseJson;

import main.json.admin.course.CourseDayJson;

import main.json.admin.course.edit.EditCourseJson;

import main.json.admin.course.edit.EditCourseActivityJson;
import main.json.admin.course.edit.EditCourseDaysJson;
import main.json.admin.course.edit.EditCourseLanguageJson;
import main.json.admin.course.edit.EditCourseLevelJson;
import main.json.admin.course.edit.EditCourseMaxStudentsJson;
import main.json.admin.course.edit.EditCoursePriceJson;
import main.json.admin.course.edit.EditCourseTypeJson;

import main.json.course.CourseSignupJson;
import main.json.course.search.CourseSearchPatternJson;

import main.model.course.Course;
import main.model.course.CourseDay;
import main.model.user.User;

@RequestMapping(value = AdminCourseControllerUrlConstants.CLASS_URL)
@RestController
public class AdminCourseController {

    @Autowired
    private LabelProvider labelProvider;

    @Autowired
    private CourseCrudService courseCrudService;

    @Autowired
    private UserCrudService userCrudService;

    @Autowired
    private AdminCourseService adminCourseService;

    @Autowired
    private AdminLanguageService adminLanguageService;

    @Autowired
    private AdminTypeService adminTypeService;

    @Autowired
    private AdminLevelService adminLevelService;

    @Autowired
    private SearchService searchService;

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.COURSE_LIST, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getCourseList() {
        CourseListJson courseList = this.adminCourseService.getCourseList();
        if( courseList.getCourses().size() <= 0 ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.list.empty"));
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.course.list.success");
        return new ResponseEntity<AdminCourseListResponseJson>(new AdminCourseListResponseJson(courseList, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.COURSE_INFO, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getCourseInfo(@PathVariable("courseID") String courseID) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.not.found"));
        CourseInfoJson courseInfoJson = this.adminCourseService.getCourseInfo(course);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.course.info.success");
        return new ResponseEntity<AdminCourseInfoResponseJson>(new AdminCourseInfoResponseJson(courseInfoJson, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.AVAILABLE_CREATING_COURSE_DATA, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getCreatingCourseData() {
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.course.creating.data.success");
        return new ResponseEntity<AdminCreatingCourseDataResponseJson>(new AdminCreatingCourseDataResponseJson(this.adminLanguageService.getLanguageList(), this.adminLevelService.getCourseLevelList(), this.adminTypeService.getCourseTypeList(), this.adminCourseService.getAllCourseDays(), messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.SEARCH_COURSES, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> searchCourses(@Valid @RequestBody CourseSearchPatternJson searchPattern) {
        try {
            Set<CourseSignupJson> results = this.searchService.seekCourses(searchPattern);
            String messageStr = this.labelProvider.getLabel("course.search.success");
            HttpStatus responseStatus = HttpStatus.OK;
            return new ResponseEntity<CourseSearchResultsResponseJson>(new CourseSearchResultsResponseJson(results, messageStr, responseStatus), responseStatus);
        }
        catch( NoResultException ex ) {
            String messageStr = this.labelProvider.getLabel(ex.getMessage());
            HttpStatus responseStatus = HttpStatus.NOT_FOUND;
            return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
        }
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.ADD_COURSE, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> addCourse(@RequestBody NewCourseJson newCourse) {
        try {
            this.adminCourseService.addCourse(newCourse);
            HttpStatus responseStatus = HttpStatus.OK;
            String messageStr = this.labelProvider.getLabel("admin.course.add.success");
            return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
        }
        catch( IllegalServiceOperationException ex ) {
            throw new HttpIllegalAccessException(this.labelProvider.getLabel("admin.course.add.error"));
        }
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.EDIT_COURSE, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editCourse(@PathVariable("courseID") String courseID, @Valid @RequestBody EditCourseJson editedCourse) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.not.found"));
        try {
            this.adminCourseService.editCourse(course, editedCourse);
            HttpStatus responseStatus = HttpStatus.OK;
            String messageStr = this.labelProvider.getLabel("admin.course.edit.success");
            return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
        }
        catch( IllegalServiceOperationException ex ) {
            throw new HttpIllegalAccessException(this.labelProvider.getLabel("admin.course.edit.error"));
        }
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.EDIT_COURSE_LANGUAGE, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editCourseLanguage(@PathVariable("courseID") String courseID, @Valid @RequestBody EditCourseLanguageJson editedCourseLanguage) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.not.found"));
        this.adminCourseService.editCourseLanguage(course, editedCourseLanguage);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.course.edit.language.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.EDIT_COURSE_TYPE, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editCourseType(@PathVariable("courseID") String courseID, @Valid @RequestBody EditCourseTypeJson editedCourseType) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.not.found"));
        this.adminCourseService.editCourseType(course, editedCourseType);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.course.edit.type.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.EDIT_COURSE_LEVEL, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editCourseLevel(@PathVariable("courseID") String courseID, @Valid @RequestBody EditCourseLevelJson editedCourseLevel) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.not.found"));
        this.adminCourseService.editCourseLevel(course, editedCourseLevel);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.course.edit.level.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.EDIT_COURSE_ACTIVITY, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editCourseActivity(@PathVariable("courseID") String courseID, @Valid @RequestBody EditCourseActivityJson editedCourseActivity) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.not.found"));
        this.adminCourseService.editCourseActivity(course, editedCourseActivity);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.course.edit.activity.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.EDIT_COURSE_DAYS, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editCourseDays(@PathVariable("courseID") String courseID, @Valid @RequestBody EditCourseDaysJson editedCourseDays) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.not.found"));
        this.adminCourseService.editCourseDays(course, editedCourseDays);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.course.edit.days.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.EDIT_COURSE_ADD_COURSE_DAY, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editCourseAddCourseDay(@PathVariable("courseID") String courseID, @Valid @RequestBody CourseDayJson courseDayJson) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.not.found"));
        this.adminCourseService.editCourseAddCourseDay(course, courseDayJson);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.course.edit.days.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.EDIT_COURSE_REMOVE_COURSE_DAY, method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editCourseRemoveCourseDay(@PathVariable("courseID") String courseID, @PathVariable("courseDayID") String courseDayID) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.not.found"));
        CourseDay courseDay = course.getCourseDay(courseDayID);
        if( courseDay == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.day.not.found"));
        this.adminCourseService.editCourseRemoveCourseDay(course, courseDay);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.course.edit.days.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.EDIT_COURSE_TEACHER, method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editCourseTeacher(@PathVariable("courseID") String courseID, @PathVariable("teacherID") String teacherID) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.not.found"));
        User teacher = this.userCrudService.findUserByID(teacherID);
        if( teacher == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.teacher.not.found"));
        try {
            this.adminCourseService.editCourseTeacher(course, teacher);
            HttpStatus responseStatus = HttpStatus.OK;
            String messageStr = this.labelProvider.getLabel("admin.course.edit.teacher.success");
            return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
        }
        catch( IllegalServiceOperationException ex ) {
            throw new HttpIllegalAccessException(this.labelProvider.getLabel("admin.course.edit.teacher.error"));
        }
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.EDIT_COURSE_ADD_TEACHER, method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editCourseAddTeacher(@PathVariable("courseID") String courseID, @PathVariable("teacherID") String teacherID) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.not.found"));
        User teacher = this.userCrudService.findUserByID(teacherID);
        if( teacher == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.teacher.not.found"));
        try {
            this.adminCourseService.editCourseAddTeacher(course, teacher);
            HttpStatus responseStatus = HttpStatus.OK;
            String messageStr = this.labelProvider.getLabel("admin.course.edit.teacher.success");
            return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
        }
        catch( IllegalServiceOperationException ex ) {
            throw new HttpIllegalAccessException(this.labelProvider.getLabel("admin.course.edit.teacher.error"));
        }
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.EDIT_COURSE_REMOVE_TEACHER, method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editCourseRemoveTeacher(@PathVariable("courseID") String courseID, @PathVariable("teacherID") String teacherID) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.not.found"));
        User teacher = this.userCrudService.findUserByID(teacherID);
        if( teacher == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.teacher.not.found"));
        this.adminCourseService.editCourseRemoveTeacher(course, teacher);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.course.remove.teacher.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.EDIT_COURSE_MAX_STUDENTS, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editCourseMaxStudents(@PathVariable("courseID") String courseID, @Valid @RequestBody EditCourseMaxStudentsJson editedCourseMaxStudents) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.not.found"));
        this.adminCourseService.editCourseMaxStudents(course, editedCourseMaxStudents);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.course.edit.maxstudents.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.EDIT_COURSE_PRICE, method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editCoursePrice(@PathVariable("courseID") String courseID, @RequestBody EditCoursePriceJson editedCoursePrice) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.not.found"));
        this.adminCourseService.editCoursePrice(course, editedCoursePrice);
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("admin.course.edit.price.success");
        return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.ADMIN)
    @RequestMapping(value = AdminCourseControllerUrlConstants.REMOVE_COURSE, method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> removeCourse(@PathVariable("courseID") String courseID) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("admin.course.not.found"));
        try {
            this.adminCourseService.removeCourse(course);
            HttpStatus responseStatus = HttpStatus.OK;
            String messageStr = this.labelProvider.getLabel("admin.course.remove.success");
            return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
        }
        catch( IllegalRemovalEntityException ex ) {
            throw new HttpIllegalAccessException(this.labelProvider.getLabel("admin.course.remove.error"));
        }
    }

}
