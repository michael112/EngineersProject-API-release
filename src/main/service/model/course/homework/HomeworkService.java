package main.service.model.course.homework;

import java.util.List;

import main.model.course.Homework;

public interface HomeworkService {

    Homework findHomeworkByID(String id);

    List<Homework> findAllHomeworks();

    void saveHomework(Homework entity);

    void updateHomework(Homework entity);

    void deleteHomework(Homework entity);

    void deleteHomeworkByID(String id);
}