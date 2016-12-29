package main.json.admin.user.field;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EmailJson {

    @NotBlank(message = "email.empty")
    @Email(message = "email.invalid")
    @Size(max = 50, message = "email.length")
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
