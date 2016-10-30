package main.json.course.homework.info.solution;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.AttachementJson;

import main.json.course.CourseUserJson;

@EqualsAndHashCode
public class HomeworkSolutionJson {

    @Getter
    private CourseUserJson student;

    @Getter
    private AttachementJson solutionFile;

    public HomeworkSolutionJson(String studentID, String studentName, AttachementJson solutionFile) {
        this.student = new CourseUserJson(studentID, studentName);
        this.solutionFile = solutionFile;
    }

}
