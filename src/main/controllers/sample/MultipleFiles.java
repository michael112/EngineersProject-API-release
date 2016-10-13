package main.controllers.sample;

import java.util.List;
import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;

public class MultipleFiles {

    @Getter
    private List<MultipartFile> files;

    public void setFiles(List<MultipartFile> files) {
        this.files.clear();
        if( files != null ) {
            this.files.addAll(files);
        }
    }

    public MultipleFiles() {
        this.files = new ArrayList<>();
    }

}
