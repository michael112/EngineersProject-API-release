package main.dao.course.file;

import java.util.List;

import main.model.course.File;

public interface FileDao {

    File findFileByID(String id);

    List<File> findAllFiles();

    void saveFile(File entity);

    void updateFile(File entity);

    void deleteFile(File entity);

}
