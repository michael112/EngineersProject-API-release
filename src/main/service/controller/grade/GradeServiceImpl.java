package main.service.controller.grade;

import main.service.controller.AbstractService;

import main.util.locale.LocaleCodeProvider;

import main.model.course.Grade;
import main.model.course.Homework;
import main.model.course.StudentGrade;
import main.model.course.Course;
import main.model.user.User;

import main.json.course.CourseUserJson;
import main.json.course.HomeworkJson;
import main.json.course.TestJson;

import main.json.course.grade.commons.CourseJson;
import main.json.course.grade.commons.GradeJson;
import main.json.course.grade.commons.StudentGradeJson;

public class GradeServiceImpl extends AbstractService implements GradeService {

    public main.json.course.grade.student.allgrades.list.GradeListJson getStudentGradeList(User student, Course course) throws IllegalArgumentException {
        if( ( student == null ) || ( course == null ) ) throw new IllegalArgumentException();
        main.json.course.grade.student.allgrades.list.GradeListJson result = new main.json.course.grade.student.allgrades.list.GradeListJson(new CourseUserJson(student.getId(), student.getFullName()), getCourseJson(course, this.localeCodeProvider.getLocaleCode()));
        for( Grade grade : course.getGrades() ) {
            GradeJson gradeJson;
            if( grade.containsGradeForUser(student) ) {
                gradeJson = new GradeJson(grade.getId(), new CourseUserJson(grade.getGradedBy().getId(), grade.getGradedBy().getFullName()), grade.getGradeTitle(), grade.getGradeDescription(), grade.getScale().name(), grade.getMaxPoints(), grade.getWeight());
                if( ( grade.getTask() != null ) && ( grade.getTask() instanceof Homework) ) {
                    gradeJson.setHomeworkFor(new HomeworkJson(grade.getTask().getId(), grade.getTask().getDate().toString(), grade.getTask().getTitle()));
                }
                if( ( grade.getTask() != null ) && ( grade.getTask() instanceof main.model.course.Test ) ) {
                    gradeJson.setTestFor(new TestJson(grade.getTask().getId(), grade.getTask().getDate().toString(), grade.getTask().getTitle()));
                }
                for( StudentGrade studentGrade : grade.getGrades() ) {
                    gradeJson.addGrade(new StudentGradeJson(studentGrade.getId(), studentGrade.getGradeValue()));
                }
                result.addGrade(gradeJson);
            }
        }
        return result;
    }

    private CourseJson getCourseJson(Course course, String languageCode) {
        CourseJson result = new CourseJson(course.getId(), course.getLanguage().getLanguageName(languageCode), course.getCourseLevel().getName(), course.getCourseType().getId(), course.getCourseType().getCourseTypeName(languageCode));
        for( User teacher : course.getTeachers() ) {
            result.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
        }
        return result;
    }

    public main.json.course.grade.teacher.allgrades.list.GradeListJson getTeacherGradeList(Course course) throws IllegalArgumentException {
        if( course == null ) throw new IllegalArgumentException();
        main.json.course.grade.teacher.allgrades.list.GradeListJson result = new main.json.course.grade.teacher.allgrades.list.GradeListJson(getCourseJson(course, this.localeCodeProvider.getLocaleCode()));
        for( Grade grade : course.getGrades() ) {
            GradeJson gradeJson = new GradeJson(grade.getId(), new CourseUserJson(grade.getGradedBy().getId(), grade.getGradedBy().getFullName()), grade.getGradeTitle(), grade.getGradeDescription(), grade.getScale().name(), grade.getMaxPoints(), grade.getWeight());
            if( ( grade.getTask() != null ) && ( grade.getTask() instanceof Homework ) ) {
                gradeJson.setHomeworkFor(new HomeworkJson(grade.getTask().getId(), grade.getTask().getDate().toString(), grade.getTask().getTitle()));
            }
            if( ( grade.getTask() != null ) && ( grade.getTask() instanceof main.model.course.Test ) ) {
                gradeJson.setTestFor(new TestJson(grade.getTask().getId(), grade.getTask().getDate().toString(), grade.getTask().getTitle()));
            }
            for( StudentGrade studentGrade : grade.getGrades() ) {
                gradeJson.addGrade(new StudentGradeJson(studentGrade.getId(), studentGrade.getGradeValue()));
            }
            result.addGrade(gradeJson);
        }
        return result;
    }

    public GradeServiceImpl(LocaleCodeProvider localeCodeProvider) {
        super(localeCodeProvider);
    }

}
