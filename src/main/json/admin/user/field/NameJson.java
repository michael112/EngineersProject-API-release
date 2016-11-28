package main.json.admin.user.field;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class NameJson {

    @Getter
    @Setter
    private String firstName;

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
