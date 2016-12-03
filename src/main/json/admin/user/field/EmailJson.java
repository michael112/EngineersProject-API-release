package main.json.admin.user.field;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EmailJson {

    @Setter
    @Getter
    private String email;

    public EmailJson() {
        super();
    }

    public EmailJson(String email) {
        this();
        this.setEmail(email);
    }

}
