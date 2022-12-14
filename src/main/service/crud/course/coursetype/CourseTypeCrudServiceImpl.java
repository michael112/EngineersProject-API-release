package main.service.crud.course.coursetype;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.dao.course.coursetype.CourseTypeDao;
import main.model.course.CourseType;

@Service("courseTypeCrudService")
@Transactional
public class CourseTypeCrudServiceImpl implements CourseTypeCrudService {

    @Autowired
    private CourseTypeDao dao;

    public CourseType findCourseTypeByID(String id) {
        return dao.findCourseTypeByID(id);
    }

    public Set<CourseType> findAllCourseTypes() {
        return dao.findAllCourseTypes();
    }

    public void saveCourseType(CourseType entity) {
        dao.saveCourseType(entity);
    }

    public void updateCourseType(CourseType entity) {
        dao.updateCourseType(entity);
    }

    public void deleteCourseType(CourseType entity) {
        dao.deleteCourseType(entity);
    }

    public void deleteCourseTypeByID(String id) {
        deleteCourseType(findCourseTypeByID(id));
    }

}
