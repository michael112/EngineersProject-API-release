package main.controllers;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import javax.annotation.PostConstruct;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.LocaleResolver;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import main.constants.rolesallowedconstants.RolesAllowedConstants;

import main.constants.urlconstants.CourseControllerUrlConstants;

import main.service.crud.course.course.CourseCrudService;

import main.util.currentUser.CurrentUserService;
import main.util.labels.LabelProvider;
import main.util.coursemembership.validator.CourseMembershipValidator;
import main.util.currentlanguagename.CurrentLanguageNameProvider;
import main.util.currentlanguagename.CurrentLanguageNameProviderImpl;

import main.util.locale.LocaleCodeProviderImpl;

import main.json.response.AbstractResponseJson;
import main.json.response.CourseInfoResponseJson;
import main.json.response.CourseListResponseJson;
import main.json.response.AvailableLngAndTypesResponseJson;

import main.json.course.AbstractCourseInfoJson;

import main.json.course.CourseListJson;
import main.json.course.AvailableLngAndTypesJson;

import main.model.user.User;
import main.model.course.Course;

import main.service.crud.language.LanguageCrudService;
import main.service.crud.course.coursetype.CourseTypeCrudService;

import main.service.controller.course.CourseService;
import main.service.controller.course.CourseServiceImpl;

import main.security.coursemembership.annotations.CourseMembershipRequired;

import main.error.exception.HttpNotFoundException;
import main.error.exception.HttpInternalServerErrorException;

@RequestMapping(value = CourseControllerUrlConstants.CLASS_URL)
@RestController
public class CourseController {

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private CourseCrudService courseCrudService;

    @Autowired
    private LabelProvider labelProvider;

    @Autowired
    private CourseMembershipValidator courseMembershipValidator;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private HttpServletRequest httpServletRequest;

    private CurrentLanguageNameProvider currentLanguageNameProvider;

    @Autowired
    private LanguageCrudService languageCrudService;

    @Autowired
    private CourseTypeCrudService courseTypeCrudService;

    private CourseService courseService;

    @PostConstruct
    public void initialize() {
        this.currentLanguageNameProvider = new CurrentLanguageNameProviderImpl(this.localeResolver, this.httpServletRequest);
        this.courseService = new CourseServiceImpl(new LocaleCodeProviderImpl(this.localeResolver, this.httpServletRequest), this.courseTypeCrudService, this.languageCrudService);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired
    @RequestMapping(value = CourseControllerUrlConstants.COURSE_INFO, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getCourseInfo(@PathVariable("id") String courseID) {
        // Przy implementacji panelu administracyjnego dodać obsługę nadawania komunikatów o płatnościach dla użytkownika

		User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("course.not.found"));

        boolean isStudent = this.courseMembershipValidator.isStudent( currentUser, course );
        boolean isTeacher = this.courseMembershipValidator.isTeacher( currentUser, course );
        if( !( isStudent ^ isTeacher ) ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.impossible"));

        AbstractCourseInfoJson courseInfo;
        if( isStudent ) {
            courseInfo = this.courseService.getCourseInfoStudent(course, currentUser);
        }
        else if( isTeacher ) {
            courseInfo = this.courseService.getCourseInfoTeacher(course, currentUser);
        }
        else throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.impossible")); // this situation is impossible to appear

        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("courseinfo.success");
        return new ResponseEntity<CourseInfoResponseJson>(new CourseInfoResponseJson(courseInfo, messageStr, responseStatus), responseStatus);
    }

    @RolesAllowed(RolesAllowedConstants.USER)
    @CourseMembershipRequired
    @RequestMapping(value = CourseControllerUrlConstants.COURSE_STUDENT_LIST, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getCourseStudentList(@PathVariable("id") String courseID) {
        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("course.not.found"));
        CourseListJson result = this.courseService.getCourseStudentList(course, this.localeResolver.resolveLocale(this.httpServletRequest).getLanguage());
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("coursestudentlist.success");
        return new ResponseEntity<CourseListResponseJson>(new CourseListResponseJson(result, messageStr, responseStatus), responseStatus);
    }

    @PermitAll
    @RequestMapping(value = CourseControllerUrlConstants.COURSE_SHOW_AVAILABLE_LANGUAGES_AND_COURSE_TYPES, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> showAvailableLanguagesAndCourseTypes() {
        AvailableLngAndTypesJson result = this.courseService.showAvailableLanguagesAndCourseTypes();
        HttpStatus responseStatus = HttpStatus.OK;
        String messageStr = this.labelProvider.getLabel("course.showavailablelanguagesandcoursetypes");
        return new ResponseEntity<AvailableLngAndTypesResponseJson>(new AvailableLngAndTypesResponseJson(result, messageStr, responseStatus), responseStatus);
    }

    /*
    @PermitAll
    @RequestMapping(value = CourseControllerUrlConstants.COURSE_SEARCH_COURSES, method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> searchCourses(@RequestBody SearchPatternJson searchPattern) {
        // toDo
    }
    */
}
