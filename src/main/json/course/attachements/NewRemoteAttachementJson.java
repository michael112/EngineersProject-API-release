package main.json.course.attachements;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

public class NewRemoteAttachementJson {

    @NotBlank(message = "newattachement.path.empty")
    @Size(max = 100, message = "newattachement.path.length")
    @Getter
    @Setter
    private String path;

    public NewRemoteAttachementJson() {
        super();
    }

    public NewRemoteAttachementJson(String path) {
        this();
        this.setPath(path);
    }

}
