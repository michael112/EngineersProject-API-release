package main.json.course.homework.edit;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EditHomeworkTitleJson {

    @Size(max = 50, message = "newhomework.title.length")
    @Getter
    @Setter
    private String title;

    public EditHomeworkTitleJson() {
        super();
    }

    public EditHomeworkTitleJson(String title) {
        this();
        this.setTitle(title);
    }

}
