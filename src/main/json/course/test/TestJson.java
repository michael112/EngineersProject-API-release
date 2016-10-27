package main.json.course.test;

import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class TestJson {

    @Size(max = 50, message = "newtest.title.size")
    @Getter
    @Setter
    private String title;

    @Pattern(regexp = "(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-\\d{4}", message = "newtest.date")
    @Getter
    @Setter
    private String date;

    @Size(max = 100, message = "newtest.description.size")
    @Getter
    @Setter
    private String description;

    public TestJson() {}

    public TestJson(String title, String date, String description) {
        this.setTitle(title);
        this.setDate(date);
        this.setDescription(description);
    }

}
