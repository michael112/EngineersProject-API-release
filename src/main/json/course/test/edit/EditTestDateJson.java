package main.json.course.test.edit;

import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EditTestDateJson {

    @Pattern(regexp = "(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-\\d{4}", message = "newtest.date")
    @Getter
    @Setter
    private String date;

    public EditTestDateJson() {}

    public EditTestDateJson(String date) {
        this.setDate(date);
    }

}
