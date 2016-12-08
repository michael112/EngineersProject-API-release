package main.json.user;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class UserSearchPatternJson {

    @Setter
    @Getter
    private String pattern;

    public UserSearchPatternJson() {
        super();
    }

    public UserSearchPatternJson(String pattern) {
        this();
        this.setPattern(pattern);
    }

}
