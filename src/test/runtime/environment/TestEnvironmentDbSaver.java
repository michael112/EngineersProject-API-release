package test.runtime.environment;

import org.springframework.beans.factory.annotation.Autowired;

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
import main.service.crud.language.LanguageCrudService;

import main.model.language.Language;
import main.model.course.*;
import main.model.placementtest.PlacementTest;
import main.model.user.User;
import main.model.user.userprofile.PlacementTestResult;

public class TestEnvironmentDbSaver {

    @Autowired
    private LanguageCrudService languageCrudService;

    @Autowired
    private CourseLevelCrudService courseLevelCrudService;

    @Autowired
    private CourseTypeCrudService courseTypeCrudService;

    @Autowired
    private PlacementTestCrudService placementTestCrudService;

    @Autowired
    private UserCrudService userCrudService;

    @Autowired
    private PlacementTestResultCrudService placementTestResultCrudService;

    @Autowired
    private FileCrudService fileCrudService;

    @Autowired
    private CourseCrudService courseCrudService;

    @Autowired
    private MessageCrudService messageCrudService;

    @Autowired
    private HomeworkCrudService homeworkCrudService;

    @Autowired
    private TestCrudService testCrudService;

    @Autowired
    private GradeCrudService gradeCrudService;

    @Autowired
    private CourseMembershipCrudService courseMembershipCrudService;

    public void saveTestEnvironment(TestEnvironment testEnvironment) {
        for( Language language : testEnvironment.getLanguages() ) {
            clearLanguageDependencies(language);
            this.languageCrudService.saveLanguage(language);
        }
        for( CourseLevel courseLevel : testEnvironment.getCourseLevels() ) {
            clearCourseLevelDependencies(courseLevel);
            this.courseLevelCrudService.saveCourseLevel(courseLevel);
        }
        for( CourseType courseType : testEnvironment.getCourseTypes() ) {
            clearCourseTypeDependencies(courseType);
            this.courseTypeCrudService.saveCourseType(courseType);
        }
        for( PlacementTest placementTest : testEnvironment.getPlacementTests() ) {
            clearPlacementTestDependencies(placementTest);
            this.placementTestCrudService.savePlacementTest(placementTest);
        }
        for( User user : testEnvironment.getUsers() ) {
            clearUserDependencies(user);
            this.userCrudService.saveUser(user);
        }
        for( PlacementTestResult placementTestResult : testEnvironment.getPlacementTestResults() ) {
            this.placementTestResultCrudService.savePlacementTestResult(placementTestResult);
        }
        for( File file : testEnvironment.getFiles() ) {
            this.fileCrudService.saveFile(file);
        }
        for( Course course : testEnvironment.getCourses() ) {
            clearCourseDependencies(course);
            this.courseCrudService.saveCourse(course);
            addTeachersToCourse(course);
        }
        for( Message message : testEnvironment.getMessages() ) {
            this.messageCrudService.saveMessage(message);
        }
        for( CourseMembership courseMembership : testEnvironment.getCourseMemberships() ) {
            clearCourseMembershipDependencies(courseMembership);
            this.courseMembershipCrudService.saveCourseMembership(courseMembership);
        }
        for( Grade grade : testEnvironment.getGrades() ) {
            AbstractHomeworkOrTest tmpTask = grade.getTask();
            grade.setTask(null);
            this.gradeCrudService.saveGrade(grade);
            grade.setTask(tmpTask);
        }
        for( Homework homework : testEnvironment.getHomeworks() ) {
            clearTaskDependencies(homework);
            this.homeworkCrudService.saveHomework(homework);
        }
        for( Test test : testEnvironment.getTests() ) {
            clearTaskDependencies(test);
            this.testCrudService.saveTest(test);
        }
        for( Grade grade : testEnvironment.getGrades() ) {
            this.gradeCrudService.updateGrade(grade);
        }
    }

    private void addTeachersToCourse(Course course) {
        for( User teacher : course.getTeachers() ) {
            teacher.addCourseAsTeacher(course);
            this.userCrudService.updateUser(teacher);
        }
    }

    private void clearLanguageDependencies(Language language) {
        language.getCourses().clear();
        language.getPlacementTests().clear();
        language.getTeachers().clear();
    }

    private void clearCourseLevelDependencies(CourseLevel courseLevel) {
        courseLevel.getCourses().clear();
    }

    private void clearCourseTypeDependencies(CourseType courseType) {
        courseType.getCourses().clear();
    }

    private void clearPlacementTestDependencies(PlacementTest placementTest) {
        placementTest.getResults().clear();
    }

    private void clearUserDependencies(User user) {
        user.getPlacementTest().clear();
        user.getMyMessages().clear();
        user.getMessages().clear();
        user.getCoursesAsTeacher().clear(); // Będą wrzucane z kursami. Może nie działać, ponieważ user jest stroną dominującą relacji.
        user.getCoursesAsStudent().clear();
    }

    private void clearCourseDependencies(Course course) {
        course.getStudents().clear();
        course.getTests().clear();
        course.getHomeworks().clear();
        course.getGrades().clear();
        course.getMessages().clear();
    }

    public void clearCourseMembershipDependencies(CourseMembership courseMembership) {
        courseMembership.getGrades().clear();
    }

    private void clearTaskDependencies(AbstractHomeworkOrTest task) {
        task.setGrade(null);
    }

}
