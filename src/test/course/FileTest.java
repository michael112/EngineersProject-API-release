package test.course;

import java.util.*;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import main.model.course.File;

import main.model.user.User;

import test.AbstractTest;

public class FileTest extends AbstractTest {

    private File sampleFile;

    @Before
    public void setUp() {
        this.sampleFile = getBasicFile(null);
    }

    @Test
    public void testFileSet() {
        Set<File> files = this.fileService.findAllFiles();

        Assert.assertNotNull(files);
    }

    @Test
    public void testGetFile() {
        File sampleFileDb = this.fileService.findFileByID(this.sampleFile.getId());
        Assert.assertNotNull(sampleFileDb);
        Assert.assertEquals(this.sampleFile, sampleFileDb);
    }

    @Test
    public void testDeleteFile() {
        this.fileService.deleteFile(this.sampleFile);

        Assert.assertNull(this.fileService.findFileByID(this.sampleFile.getId()));
    }

    @Test
    public void testUpdateSender() {
        User formerSender = this.sampleFile.getSender();
        User anotherSampleSender = getBasicUser();

        this.sampleFile.setSender(anotherSampleSender);
        this.fileService.updateFile(this.sampleFile);

        File sampleFileDb = this.fileService.findFileByID(this.sampleFile.getId());
        Assert.assertEquals(anotherSampleSender, sampleFileDb.getSender());
        Assert.assertNotEquals(formerSender, sampleFileDb.getSender());
    }

    @Test
    public void testUpdatePath() {
        String newPath = "/files2";
        this.sampleFile.setPath(newPath);
        this.fileService.updateFile(this.sampleFile);

        File sampleFileDb = this.fileService.findFileByID(this.sampleFile.getId());
        Assert.assertEquals(newPath, sampleFileDb.getPath());
    }

    @Test
    public void testUpdateDate() {
        Date newDate = new Date(2015,9,20);
        this.sampleFile.setDate(newDate);
        this.fileService.updateFile(this.sampleFile);

        File sampleFileDb = this.fileService.findFileByID(this.sampleFile.getId());
        Assert.assertEquals(newDate, sampleFileDb.getDate());
    }

    @Test
    public void testUpdateName() {
        String newName = "kwa.pdf";
        this.sampleFile.setName(newName);
        this.fileService.updateFile(this.sampleFile);

        File sampleFileDb = this.fileService.findFileByID(this.sampleFile.getId());
        Assert.assertEquals(newName, sampleFileDb.getName());
    }

}
