package main.json.course.homework.info;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.course.CourseJson;

import main.json.course.homework.info.solution.HomeworkSolutionJson;

@EqualsAndHashCode(callSuper = true)
public class HomeworkInfoTeacherJson extends AbstractHomeworkInfo {

    @Getter
    private Set<HomeworkSolutionJson> solutions;

    public void addSolution(HomeworkSolutionJson solution) {
        this.solutions.add(solution);
    }

    public HomeworkInfoTeacherJson(CourseJson course, String homeworkID, String date, String title, String description) {
        super(course, homeworkID, date, title, description);
        this.solutions = new HashSet<>();
    }

}
