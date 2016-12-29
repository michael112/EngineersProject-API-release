package main.json.user;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class UserSearchPatternJson {

    @NotBlank(message = "user.search.pattern.empty")
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
