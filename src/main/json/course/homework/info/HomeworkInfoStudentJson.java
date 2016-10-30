package main.json.course.homework.info;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;

import lombok.Getter;

import main.json.course.CourseJson;

import main.json.AttachementJson;

import main.json.course.homework.HomeworkGradeJson;

@EqualsAndHashCode(callSuper = true)
public class HomeworkInfoStudentJson extends AbstractHomeworkInfo {

    @JsonProperty("solved")
    public boolean isSolved() {
        return this.solutionFile != null;
    }

    @Getter
    private AttachementJson solutionFile;

    @JsonProperty("graded")
    public boolean isGraded() {
        return grade != null;
    }

    @Getter
    private HomeworkGradeJson grade;

    public void addAttachement(AttachementJson attachement) {
        super.addAttachement(attachement);
    }

    public HomeworkInfoStudentJson(CourseJson course, String homeworkID, String date, String title, String description) {
        super(course, homeworkID, date, title, description);
    }

    public HomeworkInfoStudentJson(CourseJson course, String homeworkID, String date, String title, String description, AttachementJson solutionFile) {
        this(course, homeworkID, date, title, description);
        this.solutionFile = solutionFile;
    }

    public HomeworkInfoStudentJson(CourseJson course, String homeworkID, String date, String title, String description, HomeworkGradeJson grade) {
        this(course, homeworkID, date, title, description);
        this.grade = grade;
    }

    public HomeworkInfoStudentJson(CourseJson course, String homeworkID, String date, String title, String description, AttachementJson solutionFile, HomeworkGradeJson grade) {
        this(course, homeworkID, date, title, description);
        this.solutionFile = solutionFile;
        this.grade = grade;
    }

}
