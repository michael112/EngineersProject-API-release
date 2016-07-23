package main.service.model.course.grade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.dao.course.grade.GradeDao;
import main.model.course.Grade;

@Service("gradeService")
@Transactional
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradeDao dao;

    public Grade findGradeByID(String id) {
        return dao.findGradeByID(id);
    }

    public List<Grade> findAllGrades() {
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
