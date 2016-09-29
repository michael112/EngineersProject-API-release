package main.service.crud.course.courselevel;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.dao.course.courselevel.CourseLevelDao;
import main.model.course.CourseLevel;

@Service("courseLevelCrudService")
@Transactional
public class CourseLevelCrudServiceImpl implements CourseLevelCrudService {

    @Autowired
    private CourseLevelDao dao;

    public CourseLevel findCourseLevelByID(String id) {
        return dao.findCourseLevelByID(id);
    }

    public Set<CourseLevel> findAllCourseLevels() {
        return dao.findAllCourseLevels();
    }

    public void saveCourseLevel(CourseLevel entity) {
        dao.saveCourseLevel(entity);
    }

    public void updateCourseLevel(CourseLevel entity) {
        dao.updateCourseLevel(entity);
    }

    public void deleteCourseLevel(CourseLevel entity) {
        dao.deleteCourseLevel(entity);
    }

    public void deleteCourseLevelByID(String id) {
        deleteCourseLevel(findCourseLevelByID(id));
    }

}
