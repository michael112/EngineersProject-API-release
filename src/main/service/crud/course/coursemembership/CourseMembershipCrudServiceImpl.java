package main.service.crud.course.coursemembership;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.dao.course.coursemembership.CourseMembershipDao;
import main.model.course.CourseMembership;

@Service("courseMembershipCrudService")
@Transactional
public class CourseMembershipCrudServiceImpl implements CourseMembershipCrudService {

    @Autowired
    private CourseMembershipDao dao;

    public CourseMembership findCourseMembershipByID(String id) {
        return dao.findCourseMembershipByID(id);
    }

    public Set<CourseMembership> findCourseMembershipsByQuery(String queryStr) {
        return dao.findCourseMembershipsByQuery(queryStr);
    }

    public Set<CourseMembership> findAllCourseMemberships() {
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
