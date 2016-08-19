package main.security.coursemembership;

import java.util.Collection;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

import org.springframework.beans.factory.annotation.Autowired;

import main.service.currentUser.CurrentUserService;

import main.service.coursemembership.validator.CourseMembershipValidator;

import main.model.user.User;
import main.model.UuidGenerator;

import main.model.course.Course;
import main.service.model.course.course.CourseService;

public class CourseMembershipRequiredVoter implements AccessDecisionVoter<Object> {

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseMembershipValidator courseMembershipValidator;

    @Autowired
    private HttpServletRequest httpServletRequest;

    private String getCourseID() {
        String currentURL = this.httpServletRequest.getRequestURL().toString();

        String http = "http://";
        String[] parts;

        if( currentURL.startsWith(http) ) {
            parts = currentURL.substring(http.length()).split("/");
        }
        else {
            parts = currentURL.split("/");
        }

        int courseIndex = Arrays.asList(parts).indexOf("course");
        if( parts.length <= (courseIndex + 1) ) {
            return null;
        }
        String courseID = parts[courseIndex + 1];
        if( !( UuidGenerator.isUUID(courseID) ) ) {
            return null;
        }

        return courseID;
    }

    public boolean supports(ConfigAttribute configAttribute) {
        return configAttribute instanceof CourseMembershipSecurityConfig;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }

    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> definition) {
        User currentUser = this.currentUserService.getCurrentUser();
        if( currentUser == null ) {
            return ACCESS_ABSTAIN;
        }

        String courseID = getCourseID();
        if( courseID == null ) {
            return ACCESS_ABSTAIN;
        }

        Course currentCourse = this.courseService.findCourseByID(courseID);
        if( currentCourse == null ) {
            return ACCESS_ABSTAIN;
        }

        boolean courseMembershipRequiredFound = false;
        for( ConfigAttribute attribute : definition ) {
            if( supports(attribute) ) {
                courseMembershipRequiredFound = true;
                switch( attribute.getAttribute() ) {
                    case "STUDENT":
                        if( this.courseMembershipValidator.isStudent(currentUser, currentCourse) ) {
                            return ACCESS_GRANTED;
                        }
                        break;
                    case "TEACHER":
                        if( this.courseMembershipValidator.isTeacher(currentUser, currentCourse) ) {
                            return ACCESS_GRANTED;
                        }
                        break;
                    case "ALL":
                        if( this.courseMembershipValidator.isStudentOrTeacher(currentUser, currentCourse) ) {
                            return ACCESS_GRANTED;
                        }
                        break;
                }
            }
        }
        return courseMembershipRequiredFound ? ACCESS_DENIED : ACCESS_ABSTAIN;
    }

}
