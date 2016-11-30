package test.runtime.environment;

import main.service.crud.course.course.CourseCrudService;
import main.service.crud.course.courselevel.CourseLevelCrudService;
import main.service.crud.course.coursemembership.CourseMembershipCrudService;
import main.service.crud.course.coursetype.CourseTypeCrudService;
import main.service.crud.course.file.FileCrudService;
import main.service.crud.course.grade.GradeCrudService;
import main.service.crud.course.homework.HomeworkCrudService;
import main.service.crud.course.message.MessageCrudService;
import main.service.crud.course.test.TestCrudService;
import main.service.crud.placementtest.PlacementTestCrudService;
import main.service.crud.user.placementtestresult.PlacementTestResultCrudService;
import main.service.crud.user.user.UserCrudService;
import main.service.crud.user.userrole.UserRoleCrudService;
import main.service.crud.language.LanguageCrudService;

public class TestEnvironmentDbSaver {

    private TestEnvironment testEnvironment;

    private UserRoleCrudService userRoleCrudService;

    private LanguageCrudService languageCrudService;

    private CourseLevelCrudService courseLevelCrudService;

    private CourseTypeCrudService courseTypeCrudService;

    private PlacementTestCrudService placementTestCrudService;

    private UserCrudService userCrudService;

    private PlacementTestResultCrudService placementTestResultCrudService;

    private FileCrudService fileCrudService;

    private CourseCrudService courseCrudService;

    private MessageCrudService messageCrudService;

    private HomeworkCrudService homeworkCrudService;

    private TestCrudService testCrudService;

    private GradeCrudService gradeCrudService;

    private CourseMembershipCrudService courseMembershipCrudService;

    public void saveTestEnvironment() {
        throw new org.apache.commons.lang3.NotImplementedException("");
    }

    public TestEnvironmentDbSaver(TestEnvironment testEnvironment) {
        this.testEnvironment = testEnvironment;
    }

}
