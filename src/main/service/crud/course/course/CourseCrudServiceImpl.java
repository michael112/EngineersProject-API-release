package main.service.crud.course.course;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.dao.course.course.CourseDao;
import main.model.course.Course;

@Service("courseCrudService")
@Transactional
public class CourseCrudServiceImpl implements CourseCrudService {

    @Autowired
    private CourseDao dao;

    public Course findCourseByID(String id) {
        return dao.findCourseByID(id);
    }

    public Set<Course> findCoursesByQuery(String queryStr) {
        return dao.findCoursesByQuery(queryStr);
    }

    public Set<Course> findAllCourses() {
        return dao.findAllCourses();
    }

    public void saveCourse(Course entity) {
        dao.saveCourse(entity);
    }

    public void updateCourse(Course entity) {
        dao.updateCourse(entity);
    }

    public void deleteCourse(Course entity) {
        dao.deleteCourse(entity);
    }

    public void deleteCourseByID(String id) {
        deleteCourse(findCourseByID(id));
    }

}
