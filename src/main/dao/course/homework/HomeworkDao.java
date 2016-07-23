package main.dao.course.homework;

import java.util.List;

import main.model.course.Homework;

public interface HomeworkDao {

    Homework findHomeworkByID(String id);

    List<Homework> findAllHomeworks();

    void saveHomework(Homework entity);

    void updateHomework(Homework entity);

    void deleteHomework(Homework entity);

}
