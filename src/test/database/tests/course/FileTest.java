package test.database.tests.course;

import java.util.Set;

import org.joda.time.DateTime;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import main.model.course.File;

import main.model.user.User;

import test.database.AbstractDbTest;

public class FileTest extends AbstractDbTest {

    private File sampleFile;

    @Before
    public void setUp() {
        super.setUp();
        this.sampleFile = getBasicFile(null);
    }

    @org.junit.After
    public void tearDown() {
        super.tearDown();
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
        DateTime newDate = new DateTime();
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
