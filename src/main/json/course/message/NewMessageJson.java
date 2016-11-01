package main.json.course.message;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class NewMessageJson {

    @NotBlank(message = "message.title.empty")
    @Size(max = 50, message = "message.title.length")
    @Getter
    @Setter
    private String title;

    @NotBlank(message = "message.content.empty")
    @Size(max = 300, message = "message.content.length")
    @Getter
    @Setter
    private String content;

    @Setter
    @Getter
    private boolean isAnnouncement;

    public NewMessageJson() {
        super();
        this.setAnnouncement(false);
    }

    public NewMessageJson(String title, String content) {
        this();
        this.setTitle(title);
        this.setContent(content);
    }

    public NewMessageJson(String title, String content, boolean isAnnouncement) {
        this(title, content);
        this.setAnnouncement(isAnnouncement);
    }

}
