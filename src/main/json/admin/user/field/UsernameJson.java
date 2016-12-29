package main.json.admin.user.field;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class UsernameJson {

    @NotBlank(message = "username.empty")
    @Size(max = 30, message = "username.length")
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
