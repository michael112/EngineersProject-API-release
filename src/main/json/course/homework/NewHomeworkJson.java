package main.json.course.homework;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.constants.validationconstants.ValidationConstants;

@EqualsAndHashCode
public class NewHomeworkJson {

    @Size(max = 50, message = "newhomework.title.length")
    @Getter
    @Setter
    private String title;

    @Pattern(regexp = ValidationConstants.DATE_REGEX, message = "newhomework.date.invalid")
    @Getter
    @Setter
    private String date;

    @Size(max = 100, message = "newhomework.description.length")
    @Getter
    @Setter
    private String description;

    public NewHomeworkJson() {
        super();
    }

    public NewHomeworkJson(String title, String date, String description) {
        this();
        this.setTitle(title);
        this.setDate(date);
        this.setDescription(description);
    }

}
