package main.service.model.course.coursemembership;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.dao.course.coursemembership.CourseMembershipDao;
import main.model.course.CourseMembership;

@Service("courseMembershipService")
@Transactional
public class CourseMembershipServiceImpl implements CourseMembershipService {

    @Autowired
    private CourseMembershipDao dao;

    public CourseMembership findCourseMembershipByID(String id) {
        return dao.findCourseMembershipByID(id);
    }

    public List<CourseMembership> findAllCourseMemberships() {
        return dao.findAllCourseMemberships();
    }

    public void saveCourseMembership(CourseMembership entity) {
        dao.saveCourseMembership(entity);
    }

    public void updateCourseMembership(CourseMembership entity) {
        dao.updateCourseMembership(entity);
    }

    public void deleteCourseMembership(CourseMembership entity) {
        dao.deleteCourseMembership(entity);
    }

    public void deleteCourseMembershipByID(String id) {
        deleteCourseMembership(findCourseMembershipByID(id));
    }

}
