package main.service.model.course.homework;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.dao.course.homework.HomeworkDao;
import main.model.course.Homework;

@Service("homeworkService")
@Transactional
public class HomeworkServiceImpl implements HomeworkService {

    @Autowired
    private HomeworkDao dao;

    public Homework findHomeworkByID(String id) {
        return dao.findHomeworkByID(id);
    }

    public Set<Homework> findAllHomeworks() {
        return dao.findAllHomeworks();
    }

    public void saveHomework(Homework entity) {
        dao.saveHomework(entity);
    }

    public void updateHomework(Homework entity) {
        dao.updateHomework(entity);
    }

    public void deleteHomework(Homework entity) {
        dao.deleteHomework(entity);
    }

    public void deleteHomeworkByID(String id) {
        deleteHomework(findHomeworkByID(id));
    }

}
