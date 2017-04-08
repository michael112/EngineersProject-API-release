package main.json.course.homework.info;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

import lombok.EqualsAndHashCode;

import main.json.AttachementJson;

@EqualsAndHashCode
public class HomeworkAttachementsJson {

    @Getter
    protected Set<AttachementJson> attachements;

    public void addAttachement(AttachementJson attachement) {
        this.attachements.add(attachement);
    }

    public HomeworkAttachementsJson() {
        this.attachements = new HashSet<>();
    }

}