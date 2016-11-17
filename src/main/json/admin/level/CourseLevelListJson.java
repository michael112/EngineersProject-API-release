package main.json.admin.level;

import java.util.Set;
import java.util.TreeSet;

import lombok.Getter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CourseLevelListJson {

    @Getter
    private Set<CourseLevelJson> levels;

    public void addLevel(CourseLevelJson level) {
        this.levels.add(level);
    }

    public CourseLevelListJson() {
        super();
        this.levels = new TreeSet<>();
    }

}
