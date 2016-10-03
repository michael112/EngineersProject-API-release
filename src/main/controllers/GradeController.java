package main.controllers;

import javax.annotation.security.RolesAllowed;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.servlet.LocaleResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import main.security.coursemembership.annotations.CourseMembershipRequired;

import main.constants.rolesallowedconstants.RolesAllowedConstants;
import main.constants.urlconstants.GradeControllerUrlConstants;

import main.model.user.User;
import main.model.course.Course;

import main.service.controller.grade.GradeService;
import main.service.crud.course.course.CourseCrudService;

import main.util.currentUser.CurrentUserService;
import main.util.coursemembership.validator.CourseMembershipValidator;
import main.util.labels.LabelProvider;

import main.json.response.AbstractResponseJson;
import main.json.response.GradeListResponseJson;

import main.json.course.grade.commons.GradeListJson;

import main.error.exception.HttpNotFoundException;
import main.error.exception.HttpInternalServerErrorException;

@RequestMapping(value = GradeControllerUrlConstants.CLASS_URL)
@RestController
public class GradeController {

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private CourseCrudService courseCrudService;

    @Autowired
    private CourseMembershipValidator courseMembershipValidator;

    @Autowired
    private LabelProvider labelProvider;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private GradeService gradeService;

    @CourseMembershipRequired
    @RolesAllowed(RolesAllowedConstants.USER)
    @RequestMapping(value=GradeControllerUrlConstants.GRADE_LIST, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<? extends AbstractResponseJson> getGradeList(@PathVariable("courseID") String courseID) {
        String impossibleError = this.labelProvider.getLabel("error.impossible");

        User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) throw new HttpInternalServerErrorException(this.labelProvider.getLabel("error.currentuser.notfound"));

        Course course = this.courseCrudService.findCourseByID(courseID);
        if( course == null ) throw new HttpNotFoundException(this.labelProvider.getLabel("course.not.found"));

        boolean isStudent = this.courseMembershipValidator.isStudent( currentUser, course );
        boolean isTeacher = this.courseMembershipValidator.isTeacher( currentUser, course );
        if( !( isStudent ^ isTeacher ) ) throw new HttpInternalServerErrorException(impossibleError);

        String localeCode = this.localeResolver.resolveLocale(this.httpServletRequest).getLanguage();
        GradeListJson gradeList;
        if( isStudent ) {
            gradeList = this.gradeService.getStudentGradeList(currentUser, course, localeCode);
        }
        else if( isTeacher ) {
            gradeList = this.gradeService.getTeacherGradeList(course, localeCode);
        }
        else throw new HttpInternalServerErrorException(impossibleError);

        String messageStr = this.labelProvider.getLabel("");
        HttpStatus responseStatus = HttpStatus.OK;
        GradeListResponseJson responseJson = new GradeListResponseJson(gradeList, messageStr, responseStatus);
        return new ResponseEntity<GradeListResponseJson>(responseJson, responseStatus);
    }
}
