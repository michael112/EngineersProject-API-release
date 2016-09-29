package test.database.tests.user;

import java.util.*;

import main.model.course.Course;
import main.model.course.CourseMembership;
import main.model.course.Message;
import main.model.language.Language;

import main.model.user.userrole.UserRole;
import main.model.user.userprofile.PlacementTestResult;
import main.model.user.userprofile.Phone;
import main.model.enums.PhoneType;
import main.model.user.userprofile.Address;
import main.model.user.User;

import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import test.database.AbstractDbTest;

public class UserTest extends AbstractDbTest {

    private User sampleUser;

    @Before
    public void setUp() {
        this.sampleUser = getBasicUser();
    }

    @Test
    public void testUserSet() {
        Set<User> users = this.userCrudService.findAllUsers();

        Assert.assertNotNull(users);
    }

    @Test
    public void testGetUser() {
        User sampleUserDb = this.userCrudService.findUserByID(this.sampleUser.getId());
        Assert.assertNotNull(sampleUserDb);
        Assert.assertEquals(this.sampleUser, sampleUserDb);
    }

    @Test
    public void testDeleteUser() {
        this.userCrudService.deleteUser(this.sampleUser);

        Assert.assertNull(this.userCrudService.findUserByID(this.sampleUser.getId()));
    }

    @Test
    public void testUpdateUsername() {
        String formerUsername = this.sampleUser.getUsername();
        String newUsername = "newusername";
        this.sampleUser.setUsername(newUsername);
        this.userCrudService.updateUser(this.sampleUser);

        User userDb = this.userCrudService.findUserByID(this.sampleUser.getId());
        Assert.assertNotEquals(formerUsername, userDb.getUsername());
        Assert.assertEquals(newUsername, userDb.getUsername());
    }
    @Test
    public void testUpdatePassword() {
        String formerPassword = this.sampleUser.getPassword();
        String newPassword = "newpassword";
        this.sampleUser.setPassword(newPassword);
        this.userCrudService.updateUser(this.sampleUser);

        User userDb = this.userCrudService.findUserByID(this.sampleUser.getId());
        Assert.assertNotEquals(formerPassword, userDb.getPassword());
        Assert.assertEquals(newPassword, userDb.getPassword());
    }
    @Test
    public void testUpdateEmail() {
        String formerEmail = this.sampleUser.getEmail();
        String newEmail = "newemail";
        this.sampleUser.setEmail(newEmail);
        this.userCrudService.updateUser(this.sampleUser);

        User userDb = this.userCrudService.findUserByID(this.sampleUser.getId());
        Assert.assertNotEquals(formerEmail, userDb.getEmail());
        Assert.assertEquals(newEmail, userDb.getEmail());
    }
    @Test
    public void testUpdateActive() {
        boolean formerActive = this.sampleUser.isActive();
        boolean newActive = !(formerActive);
        this.sampleUser.setActive(newActive);
        this.userCrudService.updateUser(this.sampleUser);

        User userDb = this.userCrudService.findUserByID(this.sampleUser.getId());
        Assert.assertNotEquals(formerActive, userDb.isActive());
        Assert.assertEquals(newActive, userDb.isActive());
    }
    @Test
    public void testUpdateAddUserRole() {
        UserRole admin = this.userRoleCrudService.findUserRoleByRoleName("ADMIN");
        this.sampleUser.addUserRole(admin);
        this.userCrudService.updateUser(this.sampleUser);

        User userDb = this.userCrudService.findUserByID(this.sampleUser.getId());
        Assert.assertEquals(true, userDb.containsUserRole(admin));
    }
    @Test
    public void testUpdateRemoveUserRole() {
        UserRole admin = this.userRoleCrudService.findUserRoleByRoleName("ADMIN");
        this.sampleUser.addUserRole(admin);
        this.userCrudService.updateUser(this.sampleUser);

        User userDbBefore = this.userCrudService.findUserByID(this.sampleUser.getId());
        userDbBefore.removeUserRole(admin);
        this.userCrudService.updateUser(userDbBefore);

        User userDbAfter = this.userCrudService.findUserByID(this.sampleUser.getId());
        Assert.assertEquals(false, userDbAfter.containsUserRole(admin));
    }
    @Test
    public void testUpdateFirstname() {
        String formerFirstname = this.sampleUser.getFirstName();
        String newFirstname = "newname";
        this.sampleUser.setFirstName(newFirstname);
        this.userCrudService.updateUser(this.sampleUser);

        User userDb = this.userCrudService.findUserByID(this.sampleUser.getId());
        Assert.assertNotEquals(formerFirstname, userDb.getFirstName());
        Assert.assertEquals(newFirstname, userDb.getFirstName());
    }
    @Test
    public void testUpdateLastname() {
        String formerLastname = this.sampleUser.getLastName();
        String newLastname = "newname";
        this.sampleUser.setLastName(newLastname);
        this.userCrudService.updateUser(this.sampleUser);

        User userDb = this.userCrudService.findUserByID(this.sampleUser.getId());
        Assert.assertNotEquals(formerLastname, userDb.getLastName());
        Assert.assertEquals(newLastname, userDb.getLastName());
    }
    @Test
    public void testUpdateAddPhone() {
        Phone newPhone = new Phone(PhoneType.LANDLINE, "(22) 15-212-12");
        this.sampleUser.addPhone(newPhone);
        this.userCrudService.updateUser(this.sampleUser);

        User userDb = this.userCrudService.findUserByID(this.sampleUser.getId());
        Assert.assertEquals(true, userDb.containsPhone(newPhone));
    }
    @Test
    public void testUpdateRemovePhone() {
        Phone newPhone = new Phone(PhoneType.LANDLINE, "(22) 15-212-12");
        this.sampleUser.addPhone(newPhone);
        this.userCrudService.updateUser(this.sampleUser);

        User userDbBefore = this.userCrudService.findUserByID(this.sampleUser.getId());
        userDbBefore.removePhone(newPhone);
        this.userCrudService.updateUser(userDbBefore);

        User userDbAfter = this.userCrudService.findUserByID(this.sampleUser.getId());
        Assert.assertEquals(false, userDbAfter.containsPhone(newPhone));
    }
    @Test
    public void testUpdateAddressStreet() {
        Address address = this.sampleUser.getAddress();
        String formerStreet = address.getStreet();
        String newStreet = "new street";
        address.setStreet(newStreet);
        this.userCrudService.updateUser(this.sampleUser);

        User userDb = this.userCrudService.findUserByID(this.sampleUser.getId());
        Address addressDb = userDb.getAddress();
        Assert.assertNotEquals(formerStreet, addressDb.getStreet());
        Assert.assertEquals(newStreet, addressDb.getStreet());
    }
    @Test
    public void testUpdateAddressHouseNumber() {
        Address address = this.sampleUser.getAddress();
        String formerHouseNumber = address.getHouseNumber();
        String newHouseNumber = "new house number";
        address.setHouseNumber(newHouseNumber);
        this.userCrudService.updateUser(this.sampleUser);

        User userDb = this.userCrudService.findUserByID(this.sampleUser.getId());
        Address addressDb = userDb.getAddress();
        Assert.assertNotEquals(formerHouseNumber, addressDb.getHouseNumber());
        Assert.assertEquals(newHouseNumber, addressDb.getHouseNumber());
    }
    @Test
    public void testUpdateAddressFlatNumber() {
        Address address = this.sampleUser.getAddress();
        String formerFlatNumber = address.getFlatNumber();
        String newFlatNumber = "new flat number";
        address.setFlatNumber(newFlatNumber);
        this.userCrudService.updateUser(this.sampleUser);

        User userDb = this.userCrudService.findUserByID(this.sampleUser.getId());
        Address addressDb = userDb.getAddress();
        Assert.assertNotEquals(formerFlatNumber, addressDb.getFlatNumber());
        Assert.assertEquals(newFlatNumber, addressDb.getFlatNumber());
    }
    @Test
    public void testUpdateAddressPostCode() {
        Address address = this.sampleUser.getAddress();
        String formerPostCode = address.getPostCode();
        String newPostCode = "new postCode";
        address.setPostCode(newPostCode);
        this.userCrudService.updateUser(this.sampleUser);

        User userDb = this.userCrudService.findUserByID(this.sampleUser.getId());
        Address addressDb = userDb.getAddress();
        Assert.assertNotEquals(formerPostCode, addressDb.getPostCode());
        Assert.assertEquals(newPostCode, addressDb.getPostCode());
    }
    @Test
    public void testUpdateAddressCity() {
        Address address = this.sampleUser.getAddress();
        String formerCity = address.getCity();
        String newCity = "new city";
        address.setCity(newCity);
        this.userCrudService.updateUser(this.sampleUser);

        User userDb = this.userCrudService.findUserByID(this.sampleUser.getId());
        Address addressDb = userDb.getAddress();
        Assert.assertNotEquals(formerCity, addressDb.getCity());
        Assert.assertEquals(newCity, addressDb.getCity());
    }
    @Test
    public void testUpdateAddPlacementTestResult() {
        PlacementTestResult newPlacementTestResult = getBasicPlacementTestResult(false);
        this.sampleUser.addPlacementTest(newPlacementTestResult);
        this.userCrudService.updateUser(this.sampleUser);

        User userDb = this.userCrudService.findUserByID(this.sampleUser.getId());
        Assert.assertEquals(true, userDb.containsPlacementTest(newPlacementTestResult));
    }
    @Test
    public void testUpdateRemovePlacementTestResult() {
        PlacementTestResult newPlacementTestResult = getBasicPlacementTestResult(false);
        this.sampleUser.addPlacementTest(newPlacementTestResult);
        this.userCrudService.updateUser(this.sampleUser);

        User userDbBefore = this.userCrudService.findUserByID(this.sampleUser.getId());
        userDbBefore.removePlacementTest(newPlacementTestResult);
        this.userCrudService.updateUser(userDbBefore);

        User userDbAfter = this.userCrudService.findUserByID(this.sampleUser.getId());
        Assert.assertEquals(false, userDbAfter.containsPlacementTest(newPlacementTestResult));
    }
    @Test
    public void testUpdateAddCourseAsStudent() {
        CourseMembership newCourseAsStudent = getBasicCourseMembership(true, "username");
        this.sampleUser.addCourseAsStudent(newCourseAsStudent);
        this.userCrudService.updateUser(this.sampleUser);

        User userDb = this.userCrudService.findUserByID(this.sampleUser.getId());
        Assert.assertEquals(true, userDb.containsCourseAsStudent(newCourseAsStudent));
    }
    @Test
    public void testUpdateRemoveCourseAsStudent() {
        CourseMembership newCourseAsStudent = getBasicCourseMembership(true, "username");
        this.sampleUser.addCourseAsStudent(newCourseAsStudent);
        this.userCrudService.updateUser(this.sampleUser);

        User userDbBefore = this.userCrudService.findUserByID(this.sampleUser.getId());
        userDbBefore.removeCourseAsStudent(newCourseAsStudent);
        this.userCrudService.updateUser(userDbBefore);

        User userDbAfter = this.userCrudService.findUserByID(this.sampleUser.getId());
        Assert.assertEquals(false, userDbAfter.containsCourseAsStudent(newCourseAsStudent));
    }
    @Test
    public void testUpdateAddMyMessage() {
        Message newMessage = new Message();
        this.sampleUser.addMyMessage(newMessage);
        this.userCrudService.updateUser(this.sampleUser);

        User userDb = this.userCrudService.findUserByID(this.sampleUser.getId());
        Assert.assertEquals(true, userDb.containsMyMessage(newMessage));
    }
    @Test
    public void testUpdateRemoveMyMessage() {
        Message newMessage = new Message();
        this.sampleUser.addMyMessage(newMessage);
        this.userCrudService.updateUser(this.sampleUser);

        User userDbBefore = this.userCrudService.findUserByID(this.sampleUser.getId());
        userDbBefore.removeMyMessage(newMessage);
        this.userCrudService.updateUser(userDbBefore);

        User userDbAfter = this.userCrudService.findUserByID(this.sampleUser.getId());
        Assert.assertEquals(false, userDbAfter.containsMyMessage(newMessage));
    }
    @Test
    public void testUpdateAddMessage() {
        Message newMessage = new Message();
        this.sampleUser.addMessage(newMessage);
        this.userCrudService.updateUser(this.sampleUser);

        User userDb = this.userCrudService.findUserByID(this.sampleUser.getId());
        Assert.assertEquals(true, userDb.containsMessage(newMessage));
    }
    @Test
    public void testUpdateRemoveMessage() {
        Message newMessage = new Message();
        this.sampleUser.addMessage(newMessage);
        this.userCrudService.updateUser(this.sampleUser);

        User userDbBefore = this.userCrudService.findUserByID(this.sampleUser.getId());
        userDbBefore.removeMessage(newMessage);
        this.userCrudService.updateUser(userDbBefore);

        User userDbAfter = this.userCrudService.findUserByID(this.sampleUser.getId());
        Assert.assertEquals(false, userDbAfter.containsMessage(newMessage));
    }
    @Test
    public void testUpdateAddTaughtLanguage() {
        Language italian = new Language("IT");
        this.languageCrudService.saveLanguage(italian);

        this.sampleUser.addTaughtLanguage(italian);
        this.userCrudService.updateUser(this.sampleUser);

        User userDb = this.userCrudService.findUserByID(this.sampleUser.getId());
        Assert.assertEquals(true, userDb.containsTaughtLanguage(italian));
    }
    @Test
    public void testUpdateRemoveTaughtLanguage() {
        Language italian = new Language("IT");
        this.languageCrudService.saveLanguage(italian);

        this.sampleUser.addTaughtLanguage(italian);
        this.userCrudService.updateUser(this.sampleUser);

        User userDbBefore = this.userCrudService.findUserByID(this.sampleUser.getId());
        userDbBefore.removeTaughtLanguage(italian);

        User userDbAfter = this.userCrudService.findUserByID(this.sampleUser.getId());
        Assert.assertEquals(false, userDbAfter.containsTaughtLanguage(italian));
    }
    @Test
    public void testUpdateAddCourseAsTeacher() {
        Course newCourseAsTeacher = getBasicCourse(true);
        this.sampleUser.addCourseAsTeacher(newCourseAsTeacher);
        this.userCrudService.updateUser(this.sampleUser);

        User userDb = this.userCrudService.findUserByID(this.sampleUser.getId());
        Assert.assertEquals(true, userDb.containsCourseAsTeacher(newCourseAsTeacher));
    }
    @Test
    public void testUpdateRemoveCourseAsTeacher() {
        Course newCourseAsTeacher = getBasicCourse(true);
        this.sampleUser.addCourseAsTeacher(newCourseAsTeacher);
        this.userCrudService.updateUser(this.sampleUser);

        User userDbBefore = this.userCrudService.findUserByID(this.sampleUser.getId());
        userDbBefore.removeCourseAsTeacher(newCourseAsTeacher);
        this.userCrudService.updateUser(userDbBefore);

        User userDbAfter = this.userCrudService.findUserByID(this.sampleUser.getId());
        Assert.assertEquals(false, userDbAfter.containsCourseAsTeacher(newCourseAsTeacher));
    }

}