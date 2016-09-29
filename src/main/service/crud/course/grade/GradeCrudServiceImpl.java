package main.service.crud.course.grade;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.dao.course.grade.GradeDao;
import main.model.course.Grade;

@Service("gradeCrudService")
@Transactional
public class GradeCrudServiceImpl implements GradeCrudService {

    @Autowired
    private GradeDao dao;

    public Grade findGradeByID(String id) {
        return dao.findGradeByID(id);
    }

    public Set<Grade> findAllGrades() {
        return dao.findAllGrades();
    }

    public void saveGrade(Grade entity) {
        dao.saveGrade(entity);
    }

    public void updateGrade(Grade entity) {
        dao.updateGrade(entity);
    }

    public void deleteGrade(Grade entity) {
        dao.deleteGrade(entity);
    }

    public void deleteGradeByID(String id) {
        deleteGrade(findGradeByID(id));
    }

}
