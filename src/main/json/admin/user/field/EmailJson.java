package main.json.admin.user.field;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EmailJson {

    @Setter
    @Getter
    private String eMail;

    public EmailJson() {
        super();
    }

    public EmailJson(String eMail) {
        this();
        this.setEMail(eMail);
    }

}
