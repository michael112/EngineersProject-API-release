package main.util.coursemembership.validator;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import main.service.crud.course.coursemembership.CourseMembershipCrudService;

import main.model.user.User;
import main.model.course.Course;
import main.model.course.CourseMembership;

@Service("courseMembershipValidator")
public class CourseMembershipValidatorImpl implements CourseMembershipValidator {

    @Autowired
    private CourseMembershipCrudService courseMembershipCrudService;

    public boolean isStudent( User user, Course course ) {
        String query = "from CourseMembership c where c.user.id = '" + user.getId() + "' and c.course.id = '" + course.getId() + "'";
        Set<CourseMembership> courseMembershipsOfUserAndCourse = this.courseMembershipCrudService.findCourseMembershipsByQuery(query);
        return (courseMembershipsOfUserAndCourse != null) && (courseMembershipsOfUserAndCourse.size() > 0);
    }

    public boolean isTeacher( User user, Course course ) {
        return course.containsTeacher(user);
    }

    public boolean isStudentOrTeacher( User user, Course course ) {
        return this.isStudent(user, course) || this.isTeacher(user, course);
    }

}
