package main.json.admin.course;

import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.constants.validationconstants.ValidationConstants;

@EqualsAndHashCode
public class CourseActivityJson {

    @Pattern(regexp = ValidationConstants.DATE_REGEX, message = "course.activity.dateFrom.invalid")
    @Getter
    @Setter
    private String dateFrom;

    @Pattern(regexp = ValidationConstants.DATE_REGEX, message = "course.activity.dateTo.invalid")
    @Getter
    @Setter
    private String dateTo;

    public CourseActivityJson() {
        super();
    }

    public CourseActivityJson(String dateFrom, String dateTo) {
        this();
        this.setDateFrom(dateFrom);
        this.setDateTo(dateTo);
    }

}
