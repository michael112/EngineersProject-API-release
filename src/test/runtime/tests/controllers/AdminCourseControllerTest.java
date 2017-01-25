package test.runtime.tests.controllers;

import java.util.HashSet;

import java.util.List;
import java.util.ArrayList;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormat;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import main.util.currentUser.CurrentUserService;

import main.util.labels.LabelProvider;
import main.util.domain.DomainURIProvider;
import main.util.locale.LocaleCodeProvider;

import main.constants.urlconstants.AdminCourseControllerUrlConstants;

import main.service.crud.course.course.CourseCrudService;
import main.service.crud.course.coursetype.CourseTypeCrudService;
import main.service.crud.course.courselevel.CourseLevelCrudService;
import main.service.crud.language.LanguageCrudService;
import main.service.crud.user.user.UserCrudService;

import main.json.admin.course.NewCourseJson;
import main.json.admin.course.CourseActivityJson;
import main.json.admin.course.CourseDayJson;

import main.json.admin.course.TeacherJson;

import main.json.admin.course.edit.EditCourseJson;

import main.json.admin.course.edit.EditCourseActivityJson;
import main.json.admin.course.edit.EditCourseDaysJson;
import main.json.admin.course.edit.EditCourseLanguageJson;
import main.json.admin.course.edit.EditCourseLevelJson;
import main.json.admin.course.edit.EditCourseMaxStudentsJson;
import main.json.admin.course.edit.EditCoursePriceJson;
import main.json.admin.course.edit.EditCourseTypeJson;

import main.model.course.Course;
import main.model.course.CourseDay;
import main.model.course.CourseLevel;
import main.model.course.CourseType;
import main.model.language.Language;
import main.model.user.User;

import test.runtime.environment.TestEnvironment;
import test.runtime.environment.TestEnvironmentBuilder;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminCourseControllerTest extends AbstractControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private LabelProvider labelProviderMock;
    @Autowired
    private DomainURIProvider domainURIProviderMock;
    @Autowired
    private CurrentUserService currentUserServiceMock;
    @Autowired
    private LocaleCodeProvider localeCodeProviderMock;

    @Autowired
    private CourseCrudService courseCrudServiceMock;
    @Autowired
    private CourseTypeCrudService courseTypeCrudServiceMock;
    @Autowired
    private CourseLevelCrudService courseLevelCrudServiceMock;
    @Autowired
    private LanguageCrudService languageCrudServiceMock;
    @Autowired
    private UserCrudService userCrudServiceMock;

    private String testedClassURI;

    private DateTimeFormatter dateTimeFormatter;

    private TestEnvironment testEnvironment;

    public void setMockito() {
        reset(labelProviderMock, domainURIProviderMock, localeCodeProviderMock, currentUserServiceMock, courseCrudServiceMock, courseTypeCrudServiceMock, courseLevelCrudServiceMock, languageCrudServiceMock, userCrudServiceMock);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        when(this.localeCodeProviderMock.getLocaleCode()).thenReturn("en");
    }

    @Before
    public void setUp() {
        setMockito();
        this.testedClassURI = setTestedClassURI(this.domainURIProviderMock, AdminCourseControllerUrlConstants.CLASS_URL);
        this.testEnvironment = TestEnvironmentBuilder.build();
        setAuthorizationMock(this.testEnvironment.getUsers().get(1)); // sampleUser 2 (admin)
        this.dateTimeFormatter = DateTimeFormat.forPattern("dd-MM-yyyy");
    }

    @Test
    public void testGetCourseList() throws Exception {
        String returnMessage = "";

        List<Course> courses = this.testEnvironment.getCourses();
        Course sampleCourse1 = courses.get(0);
        User sampleTeacher1 = new ArrayList<>(sampleCourse1.getTeachers()).get(0);
        CourseDay sampleCourseDay1 = new ArrayList<>(sampleCourse1.getCourseDays()).get(0);
        Course sampleCourse2 = courses.get(1);
        User sampleTeacher2 = new ArrayList<>(sampleCourse2.getTeachers()).get(0);
        CourseDay sampleCourseDay2 = new ArrayList<>(sampleCourse2.getCourseDays()).get(0);

        when(courseCrudServiceMock.findAllCourses()).thenReturn(new HashSet<>(courses));
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        this.mockMvc.perform(get(this.testedClassURI + '/' + AdminCourseControllerUrlConstants.COURSE_LIST)
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.courses.courses", hasSize(2)))
            .andExpect(jsonPath("$.courses.courses[?(@.courseID == \"" + sampleCourse1.getId() + "\" && @.language.id == \"" + sampleCourse1.getLanguage().getId() + "\" && @.language.name == \"" + sampleCourse1.getLanguage().getLanguageName("en") + "\" && @.courseLevel.courseLevelID == \"" + sampleCourse1.getCourseLevel().getId() + "\" && @.courseLevel.name == \"" + sampleCourse1.getCourseLevel().getName() + "\" && @.courseType.courseTypeID == \"" + sampleCourse1.getCourseType().getId() + "\" && @.courseType.name == \"" + sampleCourse1.getCourseType().getCourseTypeName("en") + "\" && @.courseActivity.dateFrom == \"" + this.dateTimeFormatter.print(sampleCourse1.getCourseActivity().getFrom()) + "\" && @.courseActivity.dateTo == \"" + this.dateTimeFormatter.print(sampleCourse1.getCourseActivity().getTo()) + "\" && @.maxStudents == " + sampleCourse1.getMaxStudents() + " )]").exists())
            .andExpect(jsonPath("$.courses.courses[?(@.courseID == \"" + sampleCourse1.getId() + "\" && @.language.id == \"" + sampleCourse1.getLanguage().getId() + "\" && @.language.name == \"" + sampleCourse1.getLanguage().getLanguageName("en") + "\" && @.courseLevel.courseLevelID == \"" + sampleCourse1.getCourseLevel().getId() + "\" && @.courseLevel.name == \"" + sampleCourse1.getCourseLevel().getName() + "\" && @.courseType.courseTypeID == \"" + sampleCourse1.getCourseType().getId() + "\" && @.courseType.name == \"" + sampleCourse1.getCourseType().getCourseTypeName("en") + "\" && @.courseActivity.dateFrom == \"" + this.dateTimeFormatter.print(sampleCourse1.getCourseActivity().getFrom()) + "\" && @.courseActivity.dateTo == \"" + this.dateTimeFormatter.print(sampleCourse1.getCourseActivity().getTo()) + "\" && @.maxStudents == " + sampleCourse1.getMaxStudents() + " )].teachers[?( @.userID == \"" + sampleTeacher1.getId() + "\" && @.name == \"" + sampleTeacher1.getFullName() + "\" )]").exists())
            .andExpect(jsonPath("$.courses.courses[?(@.courseID == \"" + sampleCourse1.getId() + "\" && @.language.id == \"" + sampleCourse1.getLanguage().getId() + "\" && @.language.name == \"" + sampleCourse1.getLanguage().getLanguageName("en") + "\" && @.courseLevel.courseLevelID == \"" + sampleCourse1.getCourseLevel().getId() + "\" && @.courseLevel.name == \"" + sampleCourse1.getCourseLevel().getName() + "\" && @.courseType.courseTypeID == \"" + sampleCourse1.getCourseType().getId() + "\" && @.courseType.name == \"" + sampleCourse1.getCourseType().getCourseTypeName("en") + "\" && @.courseActivity.dateFrom == \"" + this.dateTimeFormatter.print(sampleCourse1.getCourseActivity().getFrom()) + "\" && @.courseActivity.dateTo == \"" + this.dateTimeFormatter.print(sampleCourse1.getCourseActivity().getTo()) + "\" && @.maxStudents == " + sampleCourse1.getMaxStudents() + " )].courseDays[?( @.day == " + sampleCourseDay1.getDay().getDay() + " && @.hourFrom.hour == " + sampleCourseDay1.getHourFrom().getHour() + " && @.hourFrom.minute == " + sampleCourseDay1.getHourFrom().getMinute() + " && @.hourTo.hour == " + sampleCourseDay1.getHourTo().getHour() + " && @.hourTo.minute == " + sampleCourseDay1.getHourTo().getMinute() + " && @.courseDayID == \"" + sampleCourseDay1.getId() + "\" )]").exists())
            .andExpect(jsonPath("$.courses.courses[?(@.courseID == \"" + sampleCourse2.getId() + "\" && @.language.id == \"" + sampleCourse2.getLanguage().getId() + "\" && @.language.name == \"" + sampleCourse2.getLanguage().getLanguageName("en") + "\" && @.courseLevel.courseLevelID == \"" + sampleCourse2.getCourseLevel().getId() + "\" && @.courseLevel.name == \"" + sampleCourse2.getCourseLevel().getName() + "\" && @.courseType.courseTypeID == \"" + sampleCourse2.getCourseType().getId() + "\" && @.courseType.name == \"" + sampleCourse2.getCourseType().getCourseTypeName("en") + "\" && @.courseActivity.dateFrom == \"" + this.dateTimeFormatter.print(sampleCourse2.getCourseActivity().getFrom()) + "\" && @.courseActivity.dateTo == \"" + this.dateTimeFormatter.print(sampleCourse2.getCourseActivity().getTo()) + "\" && @.maxStudents == " + sampleCourse2.getMaxStudents() + " )]").exists())
            .andExpect(jsonPath("$.courses.courses[?(@.courseID == \"" + sampleCourse2.getId() + "\" && @.language.id == \"" + sampleCourse2.getLanguage().getId() + "\" && @.language.name == \"" + sampleCourse2.getLanguage().getLanguageName("en") + "\" && @.courseLevel.courseLevelID == \"" + sampleCourse2.getCourseLevel().getId() + "\" && @.courseLevel.name == \"" + sampleCourse2.getCourseLevel().getName() + "\" && @.courseType.courseTypeID == \"" + sampleCourse2.getCourseType().getId() + "\" && @.courseType.name == \"" + sampleCourse2.getCourseType().getCourseTypeName("en") + "\" && @.courseActivity.dateFrom == \"" + this.dateTimeFormatter.print(sampleCourse2.getCourseActivity().getFrom()) + "\" && @.courseActivity.dateTo == \"" + this.dateTimeFormatter.print(sampleCourse2.getCourseActivity().getTo()) + "\" && @.maxStudents == " + sampleCourse2.getMaxStudents() + " )].teachers[?( @.userID == \"" + sampleTeacher2.getId() + "\" && @.name == \"" + sampleTeacher2.getFullName() + "\" )]").exists())
            .andExpect(jsonPath("$.courses.courses[?(@.courseID == \"" + sampleCourse2.getId() + "\" && @.language.id == \"" + sampleCourse2.getLanguage().getId() + "\" && @.language.name == \"" + sampleCourse2.getLanguage().getLanguageName("en") + "\" && @.courseLevel.courseLevelID == \"" + sampleCourse2.getCourseLevel().getId() + "\" && @.courseLevel.name == \"" + sampleCourse2.getCourseLevel().getName() + "\" && @.courseType.courseTypeID == \"" + sampleCourse2.getCourseType().getId() + "\" && @.courseType.name == \"" + sampleCourse2.getCourseType().getCourseTypeName("en") + "\" && @.courseActivity.dateFrom == \"" + this.dateTimeFormatter.print(sampleCourse2.getCourseActivity().getFrom()) + "\" && @.courseActivity.dateTo == \"" + this.dateTimeFormatter.print(sampleCourse2.getCourseActivity().getTo()) + "\" && @.maxStudents == " + sampleCourse2.getMaxStudents() + " )].courseDays[?( @.day == " + sampleCourseDay2.getDay().getDay() + " && @.hourFrom.hour == " + sampleCourseDay2.getHourFrom().getHour() + " && @.hourFrom.minute == " + sampleCourseDay2.getHourFrom().getMinute() + " && @.hourTo.hour == " + sampleCourseDay2.getHourTo().getHour() + " && @.hourTo.minute == " + sampleCourseDay2.getHourTo().getMinute() + " && @.courseDayID == \"" + sampleCourseDay2.getId() + "\" )]").exists())
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testGetCourseInfo() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleTeacher = new ArrayList<>(sampleCourse.getTeachers()).get(0);

        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        this.mockMvc.perform(get(this.testedClassURI + '/' + sampleCourse.getId())
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.course.courseID", is(sampleCourse.getId())))
            .andExpect(jsonPath("$.course.language.id", is(sampleCourse.getLanguage().getId())))
            .andExpect(jsonPath("$.course.language.name", is(sampleCourse.getLanguage().getLanguageName("en"))))
            .andExpect(jsonPath("$.course.courseLevel.courseLevelID", is(sampleCourse.getCourseLevel().getId())))
            .andExpect(jsonPath("$.course.courseLevel.name", is(sampleCourse.getCourseLevel().getName())))
            .andExpect(jsonPath("$.course.courseType.courseTypeID", is(sampleCourse.getCourseType().getId())))
            .andExpect(jsonPath("$.course.courseType.name", is(sampleCourse.getCourseType().getCourseTypeName("en"))))
            .andExpect(jsonPath("$.course.teachers", hasSize(1)))
            .andExpect(jsonPath("$.course.teachers[0].userID", is(sampleTeacher.getId())))
            .andExpect(jsonPath("$.course.teachers[0].name", is(sampleTeacher.getFullName())))
            .andExpect(jsonPath("$.course.courseActivity.dateFrom", is(this.dateTimeFormatter.print(sampleCourse.getCourseActivity().getFrom()))))
            .andExpect(jsonPath("$.course.courseActivity.dateTo", is(this.dateTimeFormatter.print(sampleCourse.getCourseActivity().getTo()))))
            .andExpect(jsonPath("$.course.courseDays[?(@.courseDayID == \"" + new ArrayList<>(sampleCourse.getCourseDays()).get(0).getId() + "\" && @.day == " + new ArrayList<>(sampleCourse.getCourseDays()).get(0).getDay().getDay() + " && @.hourFrom.hour == " + new ArrayList<>(sampleCourse.getCourseDays()).get(0).getHourFrom().getHour() + " && @.hourFrom.minute == " + new ArrayList<>(sampleCourse.getCourseDays()).get(0).getHourFrom().getMinute() + " && @.hourTo.hour == " + new ArrayList<>(sampleCourse.getCourseDays()).get(0).getHourTo().getHour() + " && @.hourTo.minute == " + new ArrayList<>(sampleCourse.getCourseDays()).get(0).getHourTo().getMinute() + ")]").exists())
            .andExpect(jsonPath("$.course.maxStudents", is(sampleCourse.getMaxStudents())))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testGetCreatingCourseData() throws Exception {
        String returnMessage = "";

        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        when(languageCrudServiceMock.findAllLanguages()).thenReturn(new HashSet<>(this.testEnvironment.getLanguages()));
        when(courseLevelCrudServiceMock.findAllCourseLevels()).thenReturn(new HashSet<>(this.testEnvironment.getCourseLevels()));
        when(courseTypeCrudServiceMock.findAllCourseTypes()).thenReturn(new HashSet<>(this.testEnvironment.getCourseTypes()));

        this.mockMvc.perform(get(this.testedClassURI + '/' + AdminCourseControllerUrlConstants.AVAILABLE_CREATING_COURSE_DATA)
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
                .andExpect(jsonPath("$.languages.languages", hasSize(7)))
                .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + this.testEnvironment.getLanguages().get(0).getId() + "\")]").exists())
                .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + this.testEnvironment.getLanguages().get(0).getId() + "\")].languageNames[?(@.namedLanguageID == \"" + this.testEnvironment.getLanguages().get(0).getId() + "\" && @.namingLanguageID == \"" + this.testEnvironment.getLanguages().get(0).getId() + "\" && @.languageName == \"" + this.testEnvironment.getLanguages().get(0).getLanguageName(this.testEnvironment.getLanguages().get(0).getId()) + "\")]").exists())
                .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + this.testEnvironment.getLanguages().get(1).getId() + "\")]").exists())
                .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + this.testEnvironment.getLanguages().get(1).getId() + "\")].languageNames[?(@.namedLanguageID == \"" + this.testEnvironment.getLanguages().get(1).getId() + "\" && @.namingLanguageID == \"" + this.testEnvironment.getLanguages().get(0).getId() + "\" && @.languageName == \"" + this.testEnvironment.getLanguages().get(1).getLanguageName(this.testEnvironment.getLanguages().get(0).getId()) + "\")]").exists())
                .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + this.testEnvironment.getLanguages().get(2).getId() + "\")]").exists())
                .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + this.testEnvironment.getLanguages().get(2).getId() + "\")].languageNames[?(@.namedLanguageID == \"" + this.testEnvironment.getLanguages().get(2).getId() + "\" && @.namingLanguageID == \"" + this.testEnvironment.getLanguages().get(0).getId() + "\" && @.languageName == \"" + this.testEnvironment.getLanguages().get(2).getLanguageName(this.testEnvironment.getLanguages().get(0).getId()) + "\")]").exists())
                .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + this.testEnvironment.getLanguages().get(3).getId() + "\")]").exists())
                .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + this.testEnvironment.getLanguages().get(3).getId() + "\")].languageNames[?(@.namedLanguageID == \"" + this.testEnvironment.getLanguages().get(3).getId() + "\" && @.namingLanguageID == \"" + this.testEnvironment.getLanguages().get(0).getId() + "\" && @.languageName == \"" + this.testEnvironment.getLanguages().get(3).getLanguageName(this.testEnvironment.getLanguages().get(0).getId()) + "\")]").exists())
                .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + this.testEnvironment.getLanguages().get(4).getId() + "\")]").exists())
                .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + this.testEnvironment.getLanguages().get(4).getId() + "\")].languageNames[?(@.namedLanguageID == \"" + this.testEnvironment.getLanguages().get(4).getId() + "\" && @.namingLanguageID == \"" + this.testEnvironment.getLanguages().get(0).getId() + "\" && @.languageName == \"" + this.testEnvironment.getLanguages().get(4).getLanguageName(this.testEnvironment.getLanguages().get(0).getId()) + "\")]").exists())
                .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + this.testEnvironment.getLanguages().get(5).getId() + "\")]").exists())
                .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + this.testEnvironment.getLanguages().get(5).getId() + "\")].languageNames[?(@.namedLanguageID == \"" + this.testEnvironment.getLanguages().get(5).getId() + "\" && @.namingLanguageID == \"" + this.testEnvironment.getLanguages().get(0).getId() + "\" && @.languageName == \"" + this.testEnvironment.getLanguages().get(5).getLanguageName(this.testEnvironment.getLanguages().get(0).getId()) + "\")]").exists())
                .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + this.testEnvironment.getLanguages().get(6).getId() + "\")]").exists())
                .andExpect(jsonPath("$.languages.languages[?(@.languageID == \"" + this.testEnvironment.getLanguages().get(6).getId() + "\")].languageNames[?(@.namedLanguageID == \"" + this.testEnvironment.getLanguages().get(6).getId() + "\" && @.namingLanguageID == \"" + this.testEnvironment.getLanguages().get(0).getId() + "\" && @.languageName == \"" + this.testEnvironment.getLanguages().get(6).getLanguageName(this.testEnvironment.getLanguages().get(0).getId()) + "\")]").exists())
                .andExpect(jsonPath("$.courseLevels.levels", hasSize(6)))
                .andExpect(jsonPath("$.courseLevels.levels[?(@.courseLevelID == \"" + this.testEnvironment.getCourseLevels().get(0).getId() + "\" && @.name == \"" + this.testEnvironment.getCourseLevels().get(0).getName() + "\")]").exists())
                .andExpect(jsonPath("$.courseLevels.levels[?(@.courseLevelID == \"" + this.testEnvironment.getCourseLevels().get(1).getId() + "\" && @.name == \"" + this.testEnvironment.getCourseLevels().get(1).getName() + "\")]").exists())
                .andExpect(jsonPath("$.courseLevels.levels[?(@.courseLevelID == \"" + this.testEnvironment.getCourseLevels().get(2).getId() + "\" && @.name == \"" + this.testEnvironment.getCourseLevels().get(2).getName() + "\")]").exists())
                .andExpect(jsonPath("$.courseLevels.levels[?(@.courseLevelID == \"" + this.testEnvironment.getCourseLevels().get(3).getId() + "\" && @.name == \"" + this.testEnvironment.getCourseLevels().get(3).getName() + "\")]").exists())
                .andExpect(jsonPath("$.courseLevels.levels[?(@.courseLevelID == \"" + this.testEnvironment.getCourseLevels().get(4).getId() + "\" && @.name == \"" + this.testEnvironment.getCourseLevels().get(4).getName() + "\")]").exists())
                .andExpect(jsonPath("$.courseLevels.levels[?(@.courseLevelID == \"" + this.testEnvironment.getCourseLevels().get(5).getId() + "\" && @.name == \"" + this.testEnvironment.getCourseLevels().get(5).getName() + "\")]").exists())
                .andExpect(jsonPath("$.courseTypes.types", hasSize(3)))
                .andExpect(jsonPath("$.courseTypes.types[?(@.id == \"" + this.testEnvironment.getCourseTypes().get(0).getId() + "\" && @.name == \"" + this.testEnvironment.getCourseTypes().get(0).getCourseTypeName("EN") + "\")]").exists())
                .andExpect(jsonPath("$.courseTypes.types[?(@.id == \"" + this.testEnvironment.getCourseTypes().get(1).getId() + "\" && @.name == \"" + this.testEnvironment.getCourseTypes().get(1).getCourseTypeName("EN") + "\")]").exists())
                .andExpect(jsonPath("$.courseTypes.types[?(@.id == \"" + this.testEnvironment.getCourseTypes().get(2).getId() + "\" && @.name == \"" + this.testEnvironment.getCourseTypes().get(2).getCourseTypeName("EN") + "\")]").exists())
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testAddCourse() throws Exception {
        String returnMessage = "";

        Language sampleLanguage = this.testEnvironment.getLanguages().get(0);
        CourseType sampleCourseType = this.testEnvironment.getCourseTypes().get(0);
        CourseLevel sampleCourseLevel = this.testEnvironment.getCourseLevels().get(0);

        NewCourseJson newCourse = new NewCourseJson(sampleLanguage.getId(), sampleCourseType.getId(), sampleCourseLevel.getName(), new CourseActivityJson("01-06-2016", "31-08-2016"), 15, 0);
        newCourse.addCourseDay(new CourseDayJson(5, 17, 20, 20, 30));
        newCourse.addTeacher(new TeacherJson(this.testEnvironment.getUsers().get(1).getId()));

        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        when(this.languageCrudServiceMock.findLanguageByID(Mockito.any(String.class))).thenReturn(this.testEnvironment.getLanguages().get(0));
        when(this.courseTypeCrudServiceMock.findCourseTypeByID(Mockito.any(String.class))).thenReturn(this.testEnvironment.getCourseTypes().get(0));
        when(this.courseLevelCrudServiceMock.findCourseLevelByName(Mockito.any(String.class))).thenReturn(this.testEnvironment.getCourseLevels().get(0));
        when(userCrudServiceMock.findUserByID(Mockito.any(String.class))).thenReturn(this.testEnvironment.getUsers().get(2));
        doNothing().when(courseCrudServiceMock).saveCourse(Mockito.any(Course.class));

        this.mockMvc.perform(post(this.testedClassURI + '/' + AdminCourseControllerUrlConstants.ADD_COURSE)
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(newCourse))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testEditCourse() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        EditCourseJson editedCourse = new EditCourseJson(this.testEnvironment.getLanguages().get(1).getId(), this.testEnvironment.getCourseTypes().get(1).getId(), this.testEnvironment.getCourseLevels().get(0).getName(), new CourseActivityJson("01-06-2016", "31-08-2016"), 15, 0);
        editedCourse.addCourseDay(new CourseDayJson(5, 17, 20, 20, 30));
        editedCourse.addTeacher(new TeacherJson(this.testEnvironment.getUsers().get(1).getId()));

        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        when(this.languageCrudServiceMock.findLanguageByID(Mockito.any(String.class))).thenReturn(this.testEnvironment.getLanguages().get(0));
        when(this.courseTypeCrudServiceMock.findCourseTypeByID(Mockito.any(String.class))).thenReturn(this.testEnvironment.getCourseTypes().get(0));
        when(this.courseLevelCrudServiceMock.findCourseLevelByName(Mockito.any(String.class))).thenReturn(this.testEnvironment.getCourseLevels().get(0));
        when(userCrudServiceMock.findUserByID(Mockito.any(String.class))).thenReturn(this.testEnvironment.getUsers().get(2));
        doNothing().when(courseCrudServiceMock).updateCourse(Mockito.any(Course.class));

        this.mockMvc.perform(put(this.testedClassURI + '/' + sampleCourse.getId())
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(editedCourse))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testEditCourseLanguage() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        EditCourseLanguageJson editCourseLanguage = new EditCourseLanguageJson();
        editCourseLanguage.setLanguageID(this.testEnvironment.getLanguages().get(1).getId());

        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        when(this.languageCrudServiceMock.findLanguageByID(Mockito.any(String.class))).thenReturn(this.testEnvironment.getLanguages().get(0));
        doNothing().when(courseCrudServiceMock).updateCourse(Mockito.any(Course.class));

        this.mockMvc.perform(put(this.testedClassURI + '/' + sampleCourse.getId() + "/language")
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(editCourseLanguage))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testEditCourseType() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        EditCourseTypeJson editCourseType = new EditCourseTypeJson();
        editCourseType.setCourseTypeID(this.testEnvironment.getCourseTypes().get(1).getId());

        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        when(this.courseTypeCrudServiceMock.findCourseTypeByID(Mockito.any(String.class))).thenReturn(this.testEnvironment.getCourseTypes().get(0));
        doNothing().when(courseCrudServiceMock).updateCourse(Mockito.any(Course.class));

        this.mockMvc.perform(put(this.testedClassURI + '/' + sampleCourse.getId() + "/coursetype")
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(editCourseType))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testEditCourseLevel() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        EditCourseLevelJson editCourseLevel = new EditCourseLevelJson();
        editCourseLevel.setCourseLevelName(this.testEnvironment.getCourseLevels().get(0).getName());

        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        when(this.courseLevelCrudServiceMock.findCourseLevelByName(Mockito.any(String.class))).thenReturn(this.testEnvironment.getCourseLevels().get(0));
        doNothing().when(courseCrudServiceMock).updateCourse(Mockito.any(Course.class));

        this.mockMvc.perform(put(this.testedClassURI + '/' + sampleCourse.getId() + "/level")
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(editCourseLevel))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testEditCourseActivity() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        EditCourseActivityJson editCourseActivity = new EditCourseActivityJson();
        editCourseActivity.setCourseActivity(new CourseActivityJson("01-06-2016", "31-08-2016"));

        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        doNothing().when(courseCrudServiceMock).updateCourse(Mockito.any(Course.class));

        this.mockMvc.perform(put(this.testedClassURI + '/' + sampleCourse.getId() + "/activity")
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(editCourseActivity))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testEditCourseDays() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        EditCourseDaysJson editCourseDays = new EditCourseDaysJson();
        editCourseDays.addCourseDay(new CourseDayJson(5, 17, 20, 20, 30));

        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        doNothing().when(courseCrudServiceMock).updateCourse(Mockito.any(Course.class));

        this.mockMvc.perform(put(this.testedClassURI + '/' + sampleCourse.getId() + "/days")
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(editCourseDays))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testEditCourseAddCourseDay() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        CourseDayJson courseDayJson = new CourseDayJson(5, 17, 20, 20, 30);

        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        doNothing().when(courseCrudServiceMock).updateCourse(Mockito.any(Course.class));

        this.mockMvc.perform(put(this.testedClassURI + '/' + sampleCourse.getId() + "/days/add")
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(courseDayJson))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testEditCourseRemoveCourseDay() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        CourseDay courseDay = new ArrayList<>(sampleCourse.getCourseDays()).get(0);

        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        doNothing().when(courseCrudServiceMock).updateCourse(Mockito.any(Course.class));

        this.mockMvc.perform(put(this.testedClassURI + '/' + sampleCourse.getId() + "/days/remove/" + courseDay.getId())
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testEditCourseTeacher() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleTeacher = new ArrayList<>(sampleCourse.getTeachers()).get(0);

        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(userCrudServiceMock.findUserByID(Mockito.any(String.class))).thenReturn(sampleTeacher);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        doNothing().when(courseCrudServiceMock).updateCourse(Mockito.any(Course.class));

        this.mockMvc.perform(put(this.testedClassURI + '/' + sampleCourse.getId() + "/teacher/" + sampleTeacher.getId())
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testEditCourseAddTeacher() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(1);
        User sampleUser = this.testEnvironment.getUsers().get(3);

        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(userCrudServiceMock.findUserByID(Mockito.any(String.class))).thenReturn(sampleUser);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        doNothing().when(courseCrudServiceMock).updateCourse(Mockito.any(Course.class));

        this.mockMvc.perform(put(this.testedClassURI + '/' + sampleCourse.getId() + "/teacher/add/" + sampleUser.getId())
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testEditCourseRemoveTeacher() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        User sampleTeacher = new ArrayList<>(sampleCourse.getTeachers()).get(0);

        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(userCrudServiceMock.findUserByID(Mockito.any(String.class))).thenReturn(sampleTeacher);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        doNothing().when(courseCrudServiceMock).updateCourse(Mockito.any(Course.class));

        this.mockMvc.perform(put(this.testedClassURI + '/' + sampleCourse.getId() + "/teacher/remove/" + sampleTeacher.getId())
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testEditCourseMaxStudents() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        EditCourseMaxStudentsJson editCourseMaxStudents = new EditCourseMaxStudentsJson();
        editCourseMaxStudents.setMaxStudents(12);

        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        doNothing().when(courseCrudServiceMock).updateCourse(Mockito.any(Course.class));

        this.mockMvc.perform(put(this.testedClassURI + '/' + sampleCourse.getId() + "/max/students")
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(editCourseMaxStudents))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void editCoursePrice() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);
        EditCoursePriceJson editCoursePriceJson = new EditCoursePriceJson();
        editCoursePriceJson.setPrice(899.99);

        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        doNothing().when(courseCrudServiceMock).updateCourse(Mockito.any(Course.class));

        this.mockMvc.perform(put(this.testedClassURI + '/' + sampleCourse.getId() + "/price")
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(editCoursePriceJson))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testRemoveCourseWithExistingMembers() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(0);

        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        doNothing().when(courseCrudServiceMock).deleteCourse(Mockito.any(Course.class));

        this.mockMvc.perform(delete(this.testedClassURI + '/' + sampleCourse.getId())
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isInternalServerError())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(false)));
    }

    @Test
    public void testRemoveCourse() throws Exception {
        String returnMessage = "";

        Course sampleCourse = this.testEnvironment.getCourses().get(1);

        when(courseCrudServiceMock.findCourseByID(Mockito.any(String.class))).thenReturn(sampleCourse);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        doNothing().when(courseCrudServiceMock).deleteCourse(Mockito.any(Course.class));

        this.mockMvc.perform(delete(this.testedClassURI + '/' + sampleCourse.getId())
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

}
