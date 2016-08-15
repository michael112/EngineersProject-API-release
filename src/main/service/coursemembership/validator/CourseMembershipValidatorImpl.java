package main.service.coursemembership.validator;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import main.service.model.course.coursemembership.CourseMembershipService;

import main.model.user.User;
import main.model.course.Course;
import main.model.course.CourseMembership;

@Service("courseMembershipValidator")
public class CourseMembershipValidatorImpl implements CourseMembershipValidator {

    @Autowired
    private CourseMembershipService courseMembershipService;

    public boolean isStudent( User user, Course course ) {
        String query = "from CourseMembership c where c.user.id == " + user.getId() + "and c.course.id == " + course.getId();
        Set<CourseMembership> courseMembershipsOfUserAndCourse = this.courseMembershipService.findCourseMembershipsByQuery(query);
        return courseMembershipsOfUserAndCourse != null;
    }

    public boolean isTeacher( User user, Course course ) {
        return course.containsTeacher(user);
    }

    public boolean isStudentOrTeacher( User user, Course course ) {
        return this.isStudent(user, course) || this.isTeacher(user, course);
    }

}
