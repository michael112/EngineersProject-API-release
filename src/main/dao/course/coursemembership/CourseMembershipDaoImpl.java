package main.dao.course.coursemembership;

import java.util.List;

import org.springframework.stereotype.Repository;

import main.dao.AbstractDao;
import main.model.course.CourseMembership;

@Repository("courseMembershipDao")
public class CourseMembershipDaoImpl extends AbstractDao<String, CourseMembership> implements CourseMembershipDao {

    public CourseMembership findCourseMembershipByID(String id) {
        return findByID(id);
    }

    public List<CourseMembership> findAllCourseMemberships() {
        return findAll();
    }

    public void saveCourseMembership(CourseMembership entity) {
        save(entity);
    }

    public void updateCourseMembership(CourseMembership entity) {
        update(entity);
    }

    public void deleteCourseMembership(CourseMembership entity) {
        delete(entity);
    }

}
