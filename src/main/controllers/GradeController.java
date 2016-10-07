package main.controllers;

import javax.annotation.security.RolesAllowed;

import javax.annotation.PostConstruct;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.servlet.LocaleResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import main.security.coursemembership.annotations.CourseMembershipRequired;
import main.security.coursemembership.annotations.CourseMembershipType;

import main.constants.rolesallowedconstants.RolesAllowedConstants;
import main.constants.urlconstants.GradeControllerUrlConstants;

import main.model.user.User;
import main.model.course.Course;
import main.model.course.Grade;

import main.service.controller.grade.GradeService;
import main.service.controller.grade.GradeServiceImpl;

import main.service.crud.user.user.UserCrudService;

import main.service.crud.course.course.CourseCrudService;
import main.service.crud.course.grade.GradeCrudService;

import main.service.crud.course.homework.HomeworkCrudService;
import main.service.crud.course.test.TestCrudService;

import main.util.currentUser.CurrentUserService;
import main.util.coursemembership.validator.CourseMembershipValidator;
import main.util.labels.LabelProvider;

import main.util.locale.LocaleCodeProviderImpl;

import main.json.response.AbstractResponseJson;
import main.json.response.GradeListResponseJson;
import main.json.response.GradeInfoResponseJson;
import main.json.response.DefaultResponseJson;

import main.json.course.grade.commons.GradeListJson;
import main.json.course.grade.commons.GradeJson;

import main.json.course.grade.commons.NewGradeJson;

import main.json.course.grade.teacher.edit.EditFullGradeJson;
import main.json.course.grade.teacher.edit.EditGradeInfoJson;
import main.json.course.grade.teacher.edit.EditScaleJson;
import main.json.course.grade.teacher.edit.EditPointsJson;
import main.json.course.grade.teacher.edit.StudentGradeJson;

import main.error.exception.HttpNotFoundException;
import main.error.exception.HttpBadRequestException;
import main.error.exception.HttpInternalServerErrorException;

@RequestMapping(value = GradeControllerUrlConstants.CLASS_URL)
@RestController
public class GradeController {

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private UserCrudService userCrudService;

    @Autowired
    private CourseCrudService courseCrudService;

    @Autowired
    private GradeCrudService gradeCrudService;

	@Autowired
    private HomeworkCrudService homeworkCrudService;

	@Autowired
    private TestCrudService testCrudService;

    @Autowired
    private CourseMembershipValidator courseMembershipValidator;

    @Autowired
    private LabelProvider labelProvider;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private HttpServletRequest httpServletRequest;

    private GradeService gradeService;

    @PostConstruct
    public void initialize() {
        this.gradeService = new GradeServiceImpl(new LocaleCodeProviderImpl(this.localeResolver, this.httpServletRequest), this.userCrudService, this.courseCrudService, this.gradeCrudService, this.homeworkCrudService, this.testCrudService);
    }

    @CourseMembershipRequired
    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value=GradeControllerUrlConstants.GRADE_LIST, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getGradeList(@PathVariable("courseID") String courseID) {
        User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));

        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("course.not.found"));

        boolean isStudent = this.courseMembershipValidator.isStudent( currentUser, course );
        boolean isTeacher = this.courseMembershipValidator.isTeacher( currentUser, course );
        if( !( isStudent ^ isTeacher ) ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.impossible"));

        GradeListJson gradeList;
        if( isStudent ) {
            gradeList = this.gradeService.getStudentGradeList(currentUser, course);
        }
        else if( isTeacher ) {
            gradeList = this.gradeService.getTeacherGradeList(course);
        }
        else throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.impossible"));

        String messageStr = this.labelProvider.getLabel("grade.list.success");
        HttpStatus responseStatus = HttpStatus.OK;
        GradeListResponseJson responseJson = new GradeListResponseJson(gradeList, messageStr, responseStatus);
        return new ResponseEntity<GradeListResponseJson>(responseJson, responseStatus);
    }

    @CourseMembershipRequired(type=CourseMembershipType.TEACHER)
    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value=GradeControllerUrlConstants.GRADE_INFO, method=RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getGradeInfo(@PathVariable("courseID") String courseID, @PathVariable("gradeID") String gradeID) {
        Grade grade = this.gradeCrudService.findGradeByID(gradeID);
        if( ( grade == null ) || ( !( grade.getCourse().getId().equals(courseID) ) ) ) {
            throw new HttpNotFoundException(this.labelProvider.getLabel("grade.not.found"));
        }
        GradeJson gradeJson = this.gradeService.getGradeInfo(grade);
        String messageStr = this.labelProvider.getLabel("grade.info.success");
        HttpStatus responseStatus = HttpStatus.OK;
        return new ResponseEntity<GradeInfoResponseJson>(new GradeInfoResponseJson(gradeJson, messageStr, responseStatus), responseStatus);
    }

    @CourseMembershipRequired(type=CourseMembershipType.TEACHER)
    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value=GradeControllerUrlConstants.ADD_GRADE, method=RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> createNewGrade(@RequestBody NewGradeJson newGradeJson) {
        try {
            this.gradeService.createNewGrade(newGradeJson);
            String messageStr = this.labelProvider.getLabel("grade.create.success");
            HttpStatus responseStatus = HttpStatus.OK;
            return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
        }
        catch( IllegalArgumentException ex ) {
            throw new HttpBadRequestException(this.labelProvider.getLabel("grade.create.error"));
        }
    }

    @CourseMembershipRequired(type=CourseMembershipType.TEACHER)
    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value=GradeControllerUrlConstants.EDIT_GRADE, method=RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editGrade(@RequestBody EditFullGradeJson editGradeJson) {
        try {
            this.gradeService.editGrade(editGradeJson);
            String messageStr = this.labelProvider.getLabel("grade.edit.success");
            HttpStatus responseStatus = HttpStatus.OK;
            return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
        }
        catch( IllegalArgumentException ex ) {
            throw new HttpBadRequestException(this.labelProvider.getLabel("grade.edit.error"));
        }
    }

    @CourseMembershipRequired(type=CourseMembershipType.TEACHER)
    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value=GradeControllerUrlConstants.EDIT_GRADE_INFO, method=RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editGrade(@RequestBody EditGradeInfoJson editGradeJson) {
        try {
            this.gradeService.editGrade(editGradeJson);
            String messageStr = this.labelProvider.getLabel("grade.edit.success");
            HttpStatus responseStatus = HttpStatus.OK;
            return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
        }
        catch( IllegalArgumentException ex ) {
            throw new HttpBadRequestException(this.labelProvider.getLabel("grade.edit.error"));
        }
    }

    @CourseMembershipRequired(type=CourseMembershipType.TEACHER)
    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value=GradeControllerUrlConstants.EDIT_GRADE_POINTS, method=RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editGrade(@RequestBody EditPointsJson editGradeJson) {
        try {
            this.gradeService.editGrade(editGradeJson);
            String messageStr = this.labelProvider.getLabel("grade.edit.success");
            HttpStatus responseStatus = HttpStatus.OK;
            return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
        }
        catch( IllegalArgumentException ex ) {
            throw new HttpBadRequestException(this.labelProvider.getLabel("grade.edit.error"));
        }
    }

    @CourseMembershipRequired(type=CourseMembershipType.TEACHER)
    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value=GradeControllerUrlConstants.EDIT_GRADE_SCALE, method=RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editGrade(@RequestBody EditScaleJson editGradeJson) {
        try {
            this.gradeService.editGrade(editGradeJson);
            String messageStr = this.labelProvider.getLabel("grade.edit.success");
            HttpStatus responseStatus = HttpStatus.OK;
            return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
        }
        catch( IllegalArgumentException ex ) {
            throw new HttpBadRequestException(this.labelProvider.getLabel("grade.edit.error"));
        }
    }

    @CourseMembershipRequired(type=CourseMembershipType.TEACHER)
    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value=GradeControllerUrlConstants.ADD_STUDENT_GRADE, method=RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> createStudentGrade(@RequestBody StudentGradeJson studentGradeJson) {
        try {
            this.gradeService.createStudentGrade(studentGradeJson);
            String messageStr = this.labelProvider.getLabel("grade.create.success");
            HttpStatus responseStatus = HttpStatus.OK;
            return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
        }
        catch( IllegalArgumentException ex ) {
            throw new HttpBadRequestException(this.labelProvider.getLabel("grade.create.error"));
        }
    }

    @CourseMembershipRequired(type=CourseMembershipType.TEACHER)
    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value=GradeControllerUrlConstants.EDIT_STUDENT_GRADE, method=RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> editStudentGrade(@RequestBody StudentGradeJson editStudentGradeJson) {
        try {
            this.gradeService.editStudentGrade(editStudentGradeJson);
            String messageStr = this.labelProvider.getLabel("grade.edit.success");
            HttpStatus responseStatus = HttpStatus.OK;
            return new ResponseEntity<DefaultResponseJson>(new DefaultResponseJson(messageStr, responseStatus), responseStatus);
        }
        catch( IllegalArgumentException ex ) {
            throw new HttpBadRequestException(this.labelProvider.getLabel("grade.edit.error"));
        }
    }
}
