package main.json.course.search;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.constants.validationconstants.ValidationConstants;

@EqualsAndHashCode
public class CourseSearchPatternJson {

    @NotBlank(message = "course.search.languageid.empty")
    @Size(min = 2, max = 2, message = "course.search.languageid.size")
    @Getter
    @Setter
    private String language; // id

    @NotBlank(message = "course.search.coursetypeid.empty")
    @Pattern(regexp = ValidationConstants.UUID_REGEX, message = "course.search.coursetypeid.invalid")
    @Size(max = 36, message = "course.search.coursetypeid.length")
    @Getter
    @Setter
    private String courseType; // id

    @NotBlank(message = "course.search.courselevelname.empty")
    @Size(min = 2, max = 2, message = "course.search.courselevelname.length")
    @Pattern(regexp = ValidationConstants.COURSE_LEVEL_REGEX, message = "course.search.courselevelname.invalid")
    @Getter
    @Setter
    private String courseLevel; // id == name

    @Getter
    @Setter
    private List<CourseDayJson> courseDays; // nr dnia tygodnia (modulo 7)

    @Getter
    @Setter
    private List<CourseHourJson> courseHours; // godziny w formacie 0 .. 23 + minuty 0 .. 59

    public void addCourseDay(CourseDayJson courseDayJson) {
        this.courseDays.add(courseDayJson);
    }

    public void addCourseHour(CourseHourJson courseHourJson) {
        this.courseHours.add(courseHourJson);
    }

    public CourseSearchPatternJson() {
        super();
        this.courseDays = new ArrayList<>();
        this.courseHours = new ArrayList<>();
    }

    public CourseSearchPatternJson(String language, String courseType, String courseLevel) {
        this();
        this.setLanguage(language);
        this.setCourseType(courseType);
        this.setCourseLevel(courseLevel);
    }

}
