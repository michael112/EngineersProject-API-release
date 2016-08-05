package main.dao.course.course;

import java.util.Set;

import org.springframework.stereotype.Repository;

import main.dao.AbstractDao;
import main.model.course.Course;

@Repository("courseDao")
public class CourseDaoImpl extends AbstractDao<String, Course> implements CourseDao {

    public Course findCourseByID(String id) {
        return findByID(id);
    }

    public Set<Course> findAllCourses() {
        return findAll();
    }

    public void saveCourse(Course entity) {
        save(entity);
    }

    public void updateCourse(Course entity) {
        update(entity);
    }

    public void deleteCourse(Course entity) {
        delete(entity);
    }

}
