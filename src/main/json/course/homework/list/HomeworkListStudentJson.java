package main.json.course.homework.list;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.course.CourseUserJson;
import main.json.course.homework.HomeworkWithGradeJson;

@EqualsAndHashCode(callSuper = true)
public class HomeworkListStudentJson extends AbstractHomeworkListJson {

    @Getter
    private Set<HomeworkWithGradeJson> homeworks;

    public void addTeacher(CourseUserJson teacher) {
        super.addTeacher(teacher);
    }
    public void addHomework(HomeworkWithGradeJson homework) {
        this.homeworks.add(homework);
    }

    public HomeworkListStudentJson(String courseID, String languageID, String languageName, String courseLevel, String courseTypeID, String courseTypeName) {
        super(courseID, languageID, languageName, courseLevel, courseTypeID, courseTypeName);
        this.homeworks = new HashSet<>();
    }

}
