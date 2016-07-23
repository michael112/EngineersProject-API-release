package main.dao.course.homework;

import java.util.List;

import org.springframework.stereotype.Repository;

import main.dao.AbstractDao;
import main.model.course.Homework;

@Repository("homeworkDao")
public class HomeworkDaoImpl extends AbstractDao<String, Homework> implements HomeworkDao {

    public Homework findHomeworkByID(String id) {
        return findByID(id);
    }

    public List<Homework> findAllHomeworks() {
        return findAll();
    }

    public void saveHomework(Homework entity) {
        save(entity);
    }

    public void updateHomework(Homework entity) {
        update(entity);
    }

    public void deleteHomework(Homework entity) {
        delete(entity);
    }

}
