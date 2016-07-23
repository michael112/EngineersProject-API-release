package main.dao.course.grade;

import java.util.List;

import org.springframework.stereotype.Repository;

import main.dao.AbstractDao;
import main.model.course.Grade;

@Repository("gradeDao")
public class GradeDaoImpl extends AbstractDao<String, Grade> implements GradeDao {

    public Grade findGradeByID(String id) {
        return findByID(id);
    }

    public List<Grade> findAllGrades() {
        return findAll();
    }

    public void saveGrade(Grade entity) {
        save(entity);
    }

    public void updateGrade(Grade entity) {
        update(entity);
    }

    public void deleteGrade(Grade entity) {
        delete(entity);
    }

}
