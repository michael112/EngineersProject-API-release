package main.service.crud.course.coursemembership;

import java.util.Set;

import main.model.course.CourseMembership;

public interface CourseMembershipCrudService {

    CourseMembership findCourseMembershipByID(String id);

    Set<CourseMembership> findCourseMembershipsByQuery(String queryStr);

    Set<CourseMembership> findAllCourseMemberships();

    void saveCourseMembership(CourseMembership entity);

    void updateCourseMembership(CourseMembership entity);

    void deleteCourseMembership(CourseMembership entity);

    void deleteCourseMembershipByID(String id);
}