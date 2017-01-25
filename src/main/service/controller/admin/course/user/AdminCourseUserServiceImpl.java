package main.service.controller.admin.course.user;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import main.util.locale.LocaleCodeProvider;

import main.service.controller.AbstractService;

import main.service.crud.course.coursemembership.CourseMembershipCrudService;

import main.json.admin.course.user.CourseUserListJson;

import main.json.admin.course.user.CourseMembershipJson;

import main.json.course.CourseJson;
import main.json.course.CourseUserJson;

import main.model.course.Course;
import main.model.course.CourseMembership;
import main.model.user.User;

@Service("adminCourseUserService")
public class AdminCourseUserServiceImpl extends AbstractService implements AdminCourseUserService {

    private CourseMembershipCrudService courseMembershipCrudService;

    public CourseUserListJson getCourseUserList(Course course) {
        try {
            CourseUserListJson result = new CourseUserListJson();
            for( CourseMembership cm : course.getStudents() ) {
                CourseMembershipJson cmJson;
                CourseUserJson studentJson = new CourseUserJson(cm.getUser().getId(), cm.getUser().getFullName());
                if( cm.getMovedFrom() != null ) {
                    Course movedFrom = cm.getMovedFrom();
                    CourseJson movedFromJson = new CourseJson(movedFrom.getId(), movedFrom.getLanguage().getId(), movedFrom.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode()), movedFrom.getCourseLevel().getId(), movedFrom.getCourseLevel().getName(), movedFrom.getCourseType().getId(), movedFrom.getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode()));
                    for( User teacher : movedFrom.getTeachers() ) {
                        movedFromJson.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
                    }
                    cmJson = new CourseMembershipJson(studentJson, movedFromJson, cm.isActive(), cm.isResignation());
                }
                else {
                    cmJson = new CourseMembershipJson(studentJson, cm.isActive(), cm.isResignation());
                }
                result.addStudent(cmJson);
            }
            return result;
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void addCourseUser(Course course, User user) {
        try {
            CourseMembership cm = new CourseMembership(user, course, true);
            this.courseMembershipCrudService.saveCourseMembership(cm);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void suspendCourseUser(Course course, User user) {
        try {
            CourseMembership cm = course.getCourseMembership(user);
            cm.setActive(false);
            this.courseMembershipCrudService.updateCourseMembership(cm);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void activateCourseUser(Course course, User user) {
        try {
            CourseMembership cm = course.getCourseMembership(user);
            cm.setActive(true);
            this.courseMembershipCrudService.updateCourseMembership(cm);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void removeCourseUser(Course course, User user) {
        try {
            CourseMembership cm = course.getCourseMembership(user);
            this.courseMembershipCrudService.deleteCourseMembership(cm);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void moveCourseUser(Course previousCourse, Course newCourse, User user) {
        try {
            CourseMembership cm = previousCourse.getCourseMembership(user);
            cm.setMovedFrom(previousCourse);
            cm.setCourse(newCourse);
            cm.setActive(true);
            cm.setResignation(false);
            this.courseMembershipCrudService.updateCourseMembership(cm);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void applyUserChanges(Course course, User user) {
        try {
            CourseMembership cm = course.getCourseMembership(user);
            boolean active = cm.isActive();
            boolean resignation = cm.isResignation();
            if( resignation ) this.courseMembershipCrudService.deleteCourseMembership(cm);
            else if( !active ) {
                cm.setActive(true);
                this.courseMembershipCrudService.updateCourseMembership(cm);
            }
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void revertUserChanges(Course course, User user) {
        try {
            CourseMembership cm = course.getCourseMembership(user);
            boolean active = cm.isActive();
            boolean resignation = cm.isResignation();
            if( resignation ) cm.setResignation(false);
            if( !active ) {
                cm.setActive(true);
                if( cm.getMovedFrom() != null ) {
                    cm.setCourse(cm.getMovedFrom());
                    cm.setMovedFrom(null);
                }
            }
            this.courseMembershipCrudService.updateCourseMembership(cm);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    @Autowired
    public AdminCourseUserServiceImpl(LocaleCodeProvider localeCodeProvider, CourseMembershipCrudService courseMembershipCrudService) {
        super(localeCodeProvider);
        this.courseMembershipCrudService = courseMembershipCrudService;
    }

}
