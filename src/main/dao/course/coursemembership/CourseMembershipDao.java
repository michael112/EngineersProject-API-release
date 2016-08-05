package main.dao.course.coursemembership;

import java.util.Set;

import main.model.course.CourseMembership;

public interface CourseMembershipDao {

    CourseMembership findCourseMembershipByID(String id);

    Set<CourseMembership> findAllCourseMemberships();

    void saveCourseMembership(CourseMembership entity);

    void updateCourseMembership(CourseMembership entity);

    void deleteCourseMembership(CourseMembership entity);

}
