package main.json.course.test.edit;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EditTestTitleJson {

    @Size(max = 50, message = "newtest.title.size")
    @Getter
    @Setter
    private String title;

    public EditTestTitleJson() {}

    public EditTestTitleJson(String title) {
        this.setTitle(title);
    }

}
