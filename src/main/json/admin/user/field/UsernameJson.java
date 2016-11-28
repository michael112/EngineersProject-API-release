package main.json.admin.user.field;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class UsernameJson {

    @Getter
    @Setter
    private String username;

    public UsernameJson() {
        super();
    }

    public UsernameJson(String username) {
        this();
        this.setUsername(username);
    }

}
