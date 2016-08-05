package main.dao.course.file;

import java.util.Set;

import org.springframework.stereotype.Repository;

import main.dao.AbstractDao;
import main.model.course.File;

@Repository("fileDao")
public class FileDaoImpl extends AbstractDao<String, File> implements FileDao {

    public File findFileByID(String id) {
        return findByID(id);
    }

    public Set<File> findAllFiles() {
        return findAll();
    }

    public void saveFile(File entity) {
        save(entity);
    }

    public void updateFile(File entity) {
        update(entity);
    }

    public void deleteFile(File entity) {
        delete(entity);
    }

}
