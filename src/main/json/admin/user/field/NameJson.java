package main.json.admin.user.field;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class NameJson {

    @NotBlank(message = "firstname.empty")
    @Size(max = 30, message = "firstname.length")
    @Getter
    @Setter
    private String firstName;

    @NotBlank(message = "lastname.empty")
    @Size(max = 30, message = "lastname.length")
    @Getter
    @Setter
    private String lastName;

    public NameJson() {
        super();
    }

    public NameJson(String firstName, String lastName) {
        this();
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

}
