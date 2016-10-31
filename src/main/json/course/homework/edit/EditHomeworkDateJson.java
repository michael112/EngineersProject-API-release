package main.json.course.homework.edit;

import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.constants.validationconstants.ValidationConstants;

@EqualsAndHashCode
public class EditHomeworkDateJson {

    @Pattern(regexp = ValidationConstants.DATE_REGEX, message = "newhomework.date.invalid")
    @Getter
    @Setter
    private String date;

    public EditHomeworkDateJson() {
        super();
    }

    public EditHomeworkDateJson(String date) {
        this();
        this.setDate(date);
    }

}
