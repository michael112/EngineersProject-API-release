package main.dao.course.courselevel;

import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import org.springframework.stereotype.Repository;

import main.dao.AbstractDao;
import main.model.course.CourseLevel;

@Repository("courseLevelDao")
public class CourseLevelDaoImpl extends AbstractDao<String, CourseLevel> implements CourseLevelDao {

    public CourseLevel findCourseLevelByID(String id) {
        return findByID(id);
    }

    public CourseLevel findCourseLevelByName(String name) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("name", name));
        return (CourseLevel) crit.uniqueResult();
    }

    public Set<CourseLevel> findAllCourseLevels() {
        return findAll();
    }

    public void saveCourseLevel(CourseLevel entity) {
        save(entity);
    }

    public void updateCourseLevel(CourseLevel entity) {
        update(entity);
    }

    public void deleteCourseLevel(CourseLevel entity) {
        delete(entity);
    }

}
