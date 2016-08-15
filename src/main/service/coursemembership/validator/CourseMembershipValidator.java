package main.service.coursemembership.validator;

import main.model.user.User;
import main.model.course.Course;

public interface CourseMembershipValidator {

    boolean isStudent( User user, Course course );

    boolean isTeacher( User user, Course course );

    boolean isStudentOrTeacher( User user, Course course );

}

