package main.service.model.course.file;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.dao.course.file.FileDao;
import main.model.course.File;

@Service("fileService")
@Transactional
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDao dao;

    public File findFileByID(String id) {
        return dao.findFileByID(id);
    }

    public List<File> findAllFiles() {
        return dao.findAllFiles();
    }

    public void saveFile(File entity) {
        dao.saveFile(entity);
    }

    public void updateFile(File entity) {
        dao.updateFile(entity);
    }

    public void deleteFile(File entity) {
        dao.deleteFile(entity);
    }

    public void deleteFileByID(String id) {
        deleteFile(findFileByID(id));
    }

}
