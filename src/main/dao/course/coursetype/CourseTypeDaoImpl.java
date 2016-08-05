package main.dao.course.coursetype;

import java.util.Set;

import org.springframework.stereotype.Repository;

import main.dao.AbstractDao;
import main.model.course.CourseType;

@Repository("courseTypeDao")
public class CourseTypeDaoImpl extends AbstractDao<String, CourseType> implements CourseTypeDao {

    public CourseType findCourseTypeByID(String id) {
        return findByID(id);
    }

    public Set<CourseType> findAllCourseTypes() {
        return findAll();
    }

    public void saveCourseType(CourseType entity) {
        save(entity);
    }

    public void updateCourseType(CourseType entity) {
        update(entity);
    }

    public void deleteCourseType(CourseType entity) {
        delete(entity);
    }

}
