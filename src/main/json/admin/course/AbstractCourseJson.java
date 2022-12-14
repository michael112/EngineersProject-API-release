package main.json.admin.course;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.constants.validationconstants.ValidationConstants;

@EqualsAndHashCode
public abstract class AbstractCourseJson {

    @NotBlank(message = "course.languageid.empty")
    @Size(min = 2, max = 2, message = "course.languageid.size")
    @Getter
    @Setter
    private String languageID;

    @NotBlank(message = "course.coursetypeid.empty")
    @Pattern(regexp = ValidationConstants.UUID_REGEX, message = "course.coursetypeid.invalid")
    @Size(max = 36, message = "course.coursetypeid.length")
    @Getter
    @Setter
    private String courseTypeID;

    @NotBlank(message = "course.courselevelname.empty")
    @Size(min = 2, max = 2, message = "course.courselevelname.length")
    @Pattern(regexp = ValidationConstants.COURSE_LEVEL_REGEX, message = "course.courselevelname.invalid")
    @Getter
    @Setter
    private String courseLevelName;

    @Valid
    @Getter
    @Setter
    private CourseActivityJson courseActivity;

    @Valid
    @Getter
    @Setter
    private Set<CourseDayJson> courseDays;

    @Valid
    @Getter
    @Setter
    private Set<TeacherJson> teachers; // teacherID

    @Min(value = 1, message = "course.maxstudents.min")
    @Getter
    @Setter
    private int maxStudents;

    @Getter
    @Setter
    private double price;

    public void addTeacher(TeacherJson teacher) {
        this.teachers.add(teacher);
    }

    public void addCourseDay(CourseDayJson courseDayJson) {
        this.courseDays.add(courseDayJson);
    }

    public AbstractCourseJson() {
        super();
        this.courseDays = new HashSet<>();
        this.teachers = new HashSet<>();
    }

    public AbstractCourseJson(String languageID, String courseTypeID, String courseLevelName, CourseActivityJson courseActivity, int maxStudents, double price) {
        this();
        this.setLanguageID(languageID);
        this.setCourseTypeID(courseTypeID);
        this.setCourseLevelName(courseLevelName);
        this.setCourseActivity(courseActivity);
        this.setMaxStudents(maxStudents);
        this.setPrice(price);
    }

}
