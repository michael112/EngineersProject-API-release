package main.service.controller.admin.course;

import java.util.Set;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import main.util.locale.LocaleCodeProvider;

import main.error.exception.IllegalServiceOperationException;
import main.error.exception.IllegalRemovalEntityException;

import main.service.crud.course.course.CourseCrudService;

import main.service.crud.course.coursetype.CourseTypeCrudService;
import main.service.crud.course.courselevel.CourseLevelCrudService;
import main.service.crud.language.LanguageCrudService;
import main.service.crud.user.user.UserCrudService;

import main.service.controller.AbstractService;

import main.json.admin.course.view.CourseListJson;
import main.json.admin.course.view.CourseInfoJson;

import main.json.admin.course.NewCourseJson;

import main.json.admin.course.CourseDayJson;

import main.json.admin.course.edit.EditCourseJson;

import main.json.admin.course.edit.EditCourseActivityJson;
import main.json.admin.course.edit.EditCourseDaysJson;
import main.json.admin.course.edit.EditCourseLanguageJson;
import main.json.admin.course.edit.EditCourseLevelJson;
import main.json.admin.course.edit.EditCourseMaxStudentsJson;
import main.json.admin.course.edit.EditCoursePriceJson;
import main.json.admin.course.edit.EditCourseTypeJson;

import main.json.course.CourseUserJson;

import main.model.course.Course;
import main.model.user.User;
import main.model.course.CourseActivity;
import main.model.course.CourseDay;
import main.model.course.CourseMembership;
import main.model.language.Language;

@Service("adminCourseService")
public class AdminCourseServiceImpl extends AbstractService implements AdminCourseService {

    private DateFormat df;

    private CourseCrudService courseCrudService;

    private CourseTypeCrudService courseTypeCrudService;

    private CourseLevelCrudService courseLevelCrudService;

    private LanguageCrudService languageCrudService;

    private UserCrudService userCrudService;

    public CourseListJson getCourseList() {
        try {
            Set<Course> courses = this.courseCrudService.findAllCourses();
            CourseListJson result = new CourseListJson();
            for( Course course : courses ) {
                result.addCourse(this.getCourseInfo(course));
            }
            return result;
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public CourseInfoJson getCourseInfo(Course course) {
        try {
            CourseInfoJson result = new CourseInfoJson(course.getId(), course.getLanguage().getId(), course.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode()), course.getCourseLevel().getName(), course.getCourseType().getId(), course.getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode()), this.df.format(course.getCourseActivity().getFrom()), this.df.format(course.getCourseActivity().getTo()), course.getMaxStudents());
            for( CourseDay courseDay : course.getCourseDays() ) {
                result.addCourseDay(courseDay.getId(), courseDay.getDay().getDay(), courseDay.getHourFrom().getHour(), courseDay.getHourFrom().getMinute(), courseDay.getHourTo().getHour(), courseDay.getHourTo().getMinute());
            }
            for( User teacher : course.getTeachers() ) {
                result.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
            }
            return result;
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void addCourse(NewCourseJson newCourseJson) {
        try {
            Course course = new Course();
            course.setLanguage(this.languageCrudService.findLanguageByID(newCourseJson.getLanguageID()));
            course.setCourseType(this.courseTypeCrudService.findCourseTypeByID(newCourseJson.getCourseTypeID()));
            course.setCourseLevel(this.courseLevelCrudService.findCourseLevelByID(newCourseJson.getCourseLevelID()));
            course.setCourseActivity(new CourseActivity(this.df.parse(newCourseJson.getCourseActivity().getDateFrom()), this.df.parse(newCourseJson.getCourseActivity().getDateTo())));
            for( CourseDayJson courseDayJson : newCourseJson.getCourseDays() ) {
                course.addCourseDay(new CourseDay(courseDayJson.getDay(), courseDayJson.getHourFrom().getHour(), courseDayJson.getHourFrom().getMinute(), courseDayJson.getHourTo().getHour(), courseDayJson.getHourTo().getMinute()));
            }
            for( String teacher : newCourseJson.getTeachers() ) {
                this.addTeacher(course, this.userCrudService.findUserByID(teacher));
            }
            course.setMaxStudents(newCourseJson.getMaxStudents());
            course.setPrice(newCourseJson.getPrice());
            this.courseCrudService.saveCourse(course);
        }
        catch( NullPointerException | ParseException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editCourse(Course course, EditCourseJson editedCourse) {
        try {
            course.setLanguage(this.languageCrudService.findLanguageByID(editedCourse.getLanguageID()));
            course.setCourseType(this.courseTypeCrudService.findCourseTypeByID(editedCourse.getCourseTypeID()));
            course.setCourseLevel(this.courseLevelCrudService.findCourseLevelByID(editedCourse.getCourseLevelID()));
            course.setCourseActivity(new CourseActivity(this.df.parse(editedCourse.getCourseActivity().getDateFrom()), this.df.parse(editedCourse.getCourseActivity().getDateTo())));
            // remove old course days
            for( CourseDay courseDay : course.getCourseDays() ) {
                course.removeCourseDay(courseDay);
            }
            for( CourseDayJson courseDayJson : editedCourse.getCourseDays() ) {
                course.addCourseDay(new CourseDay(courseDayJson.getDay(), courseDayJson.getHourFrom().getHour(), courseDayJson.getHourFrom().getMinute(), courseDayJson.getHourTo().getHour(), courseDayJson.getHourTo().getMinute()));
            }
            // remove teachers
            for( User oldTeacher : course.getTeachers() ) {
                course.removeTeacher(oldTeacher);
            }
            for( String teacher : editedCourse.getTeachers() ) {
                this.addTeacher(course, this.userCrudService.findUserByID(teacher));
            }
            course.setMaxStudents(editedCourse.getMaxStudents());
            course.setPrice(editedCourse.getPrice());
            this.courseCrudService.updateCourse(course);
        }
        catch( NullPointerException | ParseException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editCourseLanguage(Course course, EditCourseLanguageJson editedCourseLanguage) {
        try {
            Language newLanguage = this.languageCrudService.findLanguageByID(editedCourseLanguage.getLanguageID());
            for( User teacher : course.getTeachers() ) {
                if( !( teacher.containsTaughtLanguage(newLanguage) ) ) throw new IllegalServiceOperationException(); // teacher has no qualifications to teach this language
            }
            course.setLanguage(newLanguage);
            this.courseCrudService.updateCourse(course);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editCourseType(Course course, EditCourseTypeJson editedCourseType) {
        try {
            course.setCourseType(this.courseTypeCrudService.findCourseTypeByID(editedCourseType.getCourseTypeID()));
            this.courseCrudService.updateCourse(course);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editCourseLevel(Course course, EditCourseLevelJson editedCourseLevel) {
        try {
            course.setCourseLevel(this.courseLevelCrudService.findCourseLevelByID(editedCourseLevel.getCourseLevelID()));
            this.courseCrudService.updateCourse(course);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editCourseActivity(Course course, EditCourseActivityJson editedCourseActivity) {
        try {
            course.setCourseActivity(new CourseActivity(this.df.parse(editedCourseActivity.getCourseActivity().getDateFrom()), this.df.parse(editedCourseActivity.getCourseActivity().getDateTo())));
            this.courseCrudService.updateCourse(course);
        }
        catch( NullPointerException | ParseException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editCourseDays(Course course, EditCourseDaysJson editedCourseDays) {
        try {
            // remove old course days
            for( CourseDay courseDay : course.getCourseDays() ) {
                course.removeCourseDay(courseDay);
            }
            for( CourseDayJson courseDayJson : editedCourseDays.getCourseDays() ) {
                course.addCourseDay(new CourseDay(courseDayJson.getDay(), courseDayJson.getHourFrom().getHour(), courseDayJson.getHourFrom().getMinute(), courseDayJson.getHourTo().getHour(), courseDayJson.getHourTo().getMinute()));
            }
            this.courseCrudService.updateCourse(course);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editCourseAddCourseDay(Course course, CourseDayJson courseDayJson) {
        try {
            course.addCourseDay(new CourseDay(courseDayJson.getDay(), courseDayJson.getHourFrom().getHour(), courseDayJson.getHourFrom().getMinute(), courseDayJson.getHourTo().getHour(), courseDayJson.getHourTo().getMinute()));
            this.courseCrudService.updateCourse(course);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editCourseRemoveCourseDay(Course course, CourseDay courseDay) {
        try {
            course.removeCourseDay(courseDay);
            this.courseCrudService.updateCourse(course);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editCourseTeacher(Course course, User teacher) {
        try {
            if( course.getTeachers().size() != 1 ) throw new IllegalServiceOperationException();
            else {
                // remove teachers
                for( User oldTeacher : course.getTeachers() ) {
                    course.removeTeacher(oldTeacher);
                }
                // add new teacher
                this.addTeacher(course, teacher);
                this.courseCrudService.updateCourse(course);
            }
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editCourseAddTeacher(Course course, User teacher) {
        try {
            this.addTeacher(course, teacher);
            this.courseCrudService.updateCourse(course);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void addTeacher(Course course, User teacher) {
        // has teacher qualifications to teach this language ?
        if( !( teacher.containsTaughtLanguage(course.getLanguage()) ) ) throw new IllegalServiceOperationException();
        course.addTeacher(teacher);
    }

    public void editCourseRemoveTeacher(Course course, User teacher) {
        try {
            course.removeTeacher(teacher);
            this.courseCrudService.updateCourse(course);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editCourseMaxStudents(Course course, EditCourseMaxStudentsJson editedCourseMaxStudents) {
        try {
            course.setMaxStudents(editedCourseMaxStudents.getMaxStudents());
            this.courseCrudService.updateCourse(course);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void editCoursePrice(Course course, EditCoursePriceJson editedCoursePrice) {
        try {
            course.setPrice(editedCoursePrice.getPrice());
            this.courseCrudService.updateCourse(course);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    public void removeCourse(Course course) {
        try {
            for( CourseMembership cm : course.getStudents() ) {
                if( (cm.isActive()) && (!(cm.isResignation())) ) {
                    throw new IllegalRemovalEntityException();
                }
            }
            this.courseCrudService.deleteCourse(course);
        }
        catch( NullPointerException ex ) {
            throw new IllegalArgumentException();
        }
    }

    @Autowired
    public AdminCourseServiceImpl(LocaleCodeProvider localeCodeProvider, CourseCrudService courseCrudService, CourseTypeCrudService courseTypeCrudService, CourseLevelCrudService courseLevelCrudService, LanguageCrudService languageCrudService) {
        super(localeCodeProvider);
        this.courseCrudService = courseCrudService;
        this.courseTypeCrudService = courseTypeCrudService;
        this.courseLevelCrudService = courseLevelCrudService;
        this.languageCrudService = languageCrudService;
        this.df = new SimpleDateFormat("dd-MM-yyyy");
    }

}
