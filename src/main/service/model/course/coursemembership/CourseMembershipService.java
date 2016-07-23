package main.service.model.course.coursemembership;

import java.util.List;

import main.model.course.CourseMembership;

public interface CourseMembershipService {

    CourseMembership findCourseMembershipByID(String id);

    List<CourseMembership> findAllCourseMemberships();

    void saveCourseMembership(CourseMembership entity);

    void updateCourseMembership(CourseMembership entity);

    void deleteCourseMembership(CourseMembership entity);

    void deleteCourseMembershipByID(String id);
}