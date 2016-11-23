package main.json.admin.course;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CourseActivityJson {

    @Getter
    @Setter
    private String dateFrom;

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
