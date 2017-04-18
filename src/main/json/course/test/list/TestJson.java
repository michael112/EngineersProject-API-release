package main.json.course.test.list;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class TestJson {

    @Getter
    private String testID;

    @Getter
    private String title;

    @Getter
    private String date;

    @Getter
    private String description;

    @Getter
    private boolean written;

    @Getter
    private boolean graded;

    @Getter
    private Double grade;

    @Getter
    private Double max;

    @Getter
    private String scale;

    public TestJson(String testID, String title, String date, boolean written, boolean graded) {
        this.testID = testID;
        this.title = title;
        this.date = date;
        this.written = written;
        this.graded = graded;
    }

    public TestJson(String testID, String title, String date, String description, boolean written, boolean graded) {
        this(testID, title, date, written, graded);
        this.description = description;
    }

    public TestJson(String testID, String title, String date, boolean written, boolean graded, Double grade, String scale) {
        this(testID, title, date, written, graded);
        this.grade = grade;
        this.scale = scale;
    }

    public TestJson(String testID, String title, String date, String description, boolean written, boolean graded, Double grade, String scale) {
        this(testID, title, date, written, graded);
        this.description = description;
        this.grade = grade;
        this.scale = scale;
    }

    public TestJson(String testID, String title, String date, boolean written, boolean graded, Double grade, Double max, String scale) {
        this(testID, title, date, written, graded);
        this.grade = grade;
        this.scale = scale;
        this.max = max;
    }

    public TestJson(String testID, String title, String date, String description, boolean written, boolean graded, Double grade, Double max, String scale) {
        this(testID, title, date, written, graded);
        this.description = description;
        this.grade = grade;
        this.scale = scale;
        this.max = max;
    }

}
