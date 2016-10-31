package main.json.course.homework.edit;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EditHomeworkDescriptionJson {

    @Size(max = 100, message = "newhomework.description.length")
    @Getter
    @Setter
    private String description;

    public EditHomeworkDescriptionJson() {
        super();
    }

    public EditHomeworkDescriptionJson(String description) {
        this();
        this.setDescription(description);
    }

}
