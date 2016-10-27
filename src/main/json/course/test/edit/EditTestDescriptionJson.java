package main.json.course.test.edit;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode

public class EditTestDescriptionJson {

    @Size(max = 100, message = "newtest.description.size")
    @Getter
    @Setter
    private String description;

    public EditTestDescriptionJson() {}

    public EditTestDescriptionJson(String description) {
        this.setDescription(description);
    }

}
