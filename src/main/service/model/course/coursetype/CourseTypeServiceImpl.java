package main.service.model.course.coursetype;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.dao.course.coursetype.CourseTypeDao;
import main.model.course.CourseType;

@Service("courseTypeService")
@Transactional
public class CourseTypeServiceImpl implements CourseTypeService {

    @Autowired
    private CourseTypeDao dao;

    public CourseType findCourseTypeByID(String id) {
        return dao.findCourseTypeByID(id);
    }

    public List<CourseType> findAllCourseTypes() {
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
