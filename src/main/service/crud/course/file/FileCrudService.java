package main.service.crud.course.file;

import java.util.Set;

import main.model.course.File;

public interface FileCrudService {

    File findFileByID(String id);

    Set<File> findAllFiles();

    void saveFile(File entity);

    void updateFile(File entity);

    void deleteFile(File entity);

    void deleteFileByID(String id);
}