package main.json.course;

import java.util.Set;
import java.util.HashSet;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CourseDaySetJson {

    @Getter
    private Set<CourseDayJson> days;

    public void addDay(CourseDayJson day) {
        this.days.add(day);
    }

    public CourseDaySetJson() {
        this.days = new HashSet<>();
    }

}