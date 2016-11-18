package test.runtime.tests.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Assert;

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

import main.constants.urlconstants.AdminUserControllerUrlConstants;

import main.service.crud.user.user.UserCrudService;

import main.json.admin.user.AccountJson;

import main.model.user.userprofile.Phone;
import main.model.user.User;
import main.model.course.CourseMembership;
import main.model.course.Course;

import test.runtime.environment.TestEnvironment;
import test.runtime.environment.TestEnvironmentBuilder;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminUserControllerTest extends AbstractControllerTest {

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
    private UserCrudService userCrudServiceMock;

    private String testedClassURI;

    private TestEnvironment testEnvironment;

    public void setMockito() {
        reset(labelProviderMock, domainURIProviderMock, localeCodeProviderMock, currentUserServiceMock, userCrudServiceMock);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        when(this.localeCodeProviderMock.getLocaleCode()).thenReturn("en");
    }

    @Before
    public void setUp() {
        setMockito();
        this.testedClassURI = setTestedClassURI(this.domainURIProviderMock, AdminUserControllerUrlConstants.CLASS_URL);
        this.testEnvironment = TestEnvironmentBuilder.build();
        setAuthorizationMock(this.testEnvironment.getUsers().get(1)); // sampleUser 2 (admin)
    }

    @Test
    public void testGetAccountList() throws Exception {
        String returnMessage = "";

        List<User> allUsers = this.testEnvironment.getUsers();

        when(userCrudServiceMock.findAllUsers()).thenReturn(new HashSet<>(allUsers));
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        this.mockMvc.perform(get(this.testedClassURI + '/' + AdminUserControllerUrlConstants.ACCOUNT_LIST)
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("users.users", hasSize(3)))
//
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testAddAccount() throws Exception {
        Assert.fail();

        /*
        String returnMessage = "";

        AccountJson newAccount = new AccountJson();

        this.mockMvc.perform(post(this.testedClassURI + '/' + AdminUserControllerUrlConstants.ADD_ACCOUNT)
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(newAccount))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
        */
    }

    @Test
    public void testGetAccountInfoStudent() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(0); // sampleUser1
        Phone sampleUserPhone = new ArrayList<>(sampleUser.getPhone()).get(0);
        CourseMembership sampleUserCourseMembership = new ArrayList<>(sampleUser.getCoursesAsStudent()).get(0);
        Course sampleUserCourse = sampleUserCourseMembership.getCourse();
        User sampleCourseTeacher = new ArrayList<>(sampleUserCourse.getTeachers()).get(0);

        when(userCrudServiceMock.findUserByID(Mockito.any(String.class))).thenReturn(sampleUser);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        this.mockMvc.perform(get(this.testedClassURI + '/' + sampleUser.getId())
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("user.userID", is(sampleUser.getId())))
            .andExpect(jsonPath("user.username", is(sampleUser.getUsername())))
            .andExpect(jsonPath("user.firstName", is(sampleUser.getFirstName())))
            .andExpect(jsonPath("user.lastName", is(sampleUser.getLastName())))
            .andExpect(jsonPath("user.email", is(sampleUser.getEmail())))
            .andExpect(jsonPath("user.phone", hasSize(1)))
            .andExpect(jsonPath("user.phone[0].phoneId", is(sampleUserPhone.getId())))
            .andExpect(jsonPath("user.phone[0].phoneNumber", is(sampleUserPhone.getPhoneNumber())))
            .andExpect(jsonPath("user.phone[0].phoneType", is(sampleUserPhone.getPhoneType().name())))
            .andExpect(jsonPath("user.address.street", is(sampleUser.getAddress().getStreet())))
            .andExpect(jsonPath("user.address.houseNumber", is(sampleUser.getAddress().getHouseNumber())))
            .andExpect(jsonPath("user.address.flatNumber", is(sampleUser.getAddress().getFlatNumber())))
            .andExpect(jsonPath("user.address.postCode", is(sampleUser.getAddress().getPostCode())))
            .andExpect(jsonPath("user.address.city", is(sampleUser.getAddress().getCity())))
            .andExpect(jsonPath("user.coursesAsStudent", hasSize(1)))
            .andExpect(jsonPath("user.coursesAsStudent[0].course.courseID", is(sampleUserCourse.getId())))
            .andExpect(jsonPath("user.coursesAsStudent[0].course.language.id", is(sampleUserCourse.getLanguage().getId())))
            .andExpect(jsonPath("user.coursesAsStudent[0].course.language.name", is(sampleUserCourse.getLanguage().getLanguageName("en"))))
            .andExpect(jsonPath("user.coursesAsStudent[0].course.courseLevel", is(sampleUserCourse.getCourseLevel().getName())))
            .andExpect(jsonPath("user.coursesAsStudent[0].course.courseType.courseTypeID", is(sampleUserCourse.getCourseType().getId())))
            .andExpect(jsonPath("user.coursesAsStudent[0].course.courseType.name", is(sampleUserCourse.getCourseType().getCourseTypeName("en"))))
            .andExpect(jsonPath("user.coursesAsStudent[0].course.teachers", hasSize(1)))
            .andExpect(jsonPath("user.coursesAsStudent[0].course.teachers[0].userID", is(sampleCourseTeacher.getId())))
            .andExpect(jsonPath("user.coursesAsStudent[0].course.teachers[0].name", is(sampleCourseTeacher.getFullName())))
            .andExpect(jsonPath("user.coursesAsStudent[0].active", is(sampleUserCourseMembership.isActive())))
            .andExpect(jsonPath("user.coursesAsStudent[0].resignation", is(sampleUserCourseMembership.isResignation())))
            .andExpect(jsonPath("user.coursesAsTeacher", hasSize(0)))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testGetAccountInfoTeacher() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(2); // sampleTeacher1
        Phone sampleUserPhone = new ArrayList<>(sampleUser.getPhone()).get(0);
        Course sampleTeacherCourse = new ArrayList<>(sampleUser.getCoursesAsTeacher()).get(0);
        User sampleCourseTeacher = new ArrayList<>(sampleTeacherCourse.getTeachers()).get(0);

        when(userCrudServiceMock.findUserByID(Mockito.any(String.class))).thenReturn(sampleUser);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);

        this.mockMvc.perform(get(this.testedClassURI + '/' + sampleUser.getId())
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("user.userID", is(sampleUser.getId())))
            .andExpect(jsonPath("user.username", is(sampleUser.getUsername())))
            .andExpect(jsonPath("user.firstName", is(sampleUser.getFirstName())))
            .andExpect(jsonPath("user.lastName", is(sampleUser.getLastName())))
            .andExpect(jsonPath("user.email", is(sampleUser.getEmail())))
            .andExpect(jsonPath("user.phone", hasSize(1)))
            .andExpect(jsonPath("user.phone[0].phoneId", is(sampleUserPhone.getId())))
            .andExpect(jsonPath("user.phone[0].phoneNumber", is(sampleUserPhone.getPhoneNumber())))
            .andExpect(jsonPath("user.phone[0].phoneType", is(sampleUserPhone.getPhoneType().name())))
            .andExpect(jsonPath("user.address.street", is(sampleUser.getAddress().getStreet())))
            .andExpect(jsonPath("user.address.houseNumber", is(sampleUser.getAddress().getHouseNumber())))
            .andExpect(jsonPath("user.address.flatNumber", is(sampleUser.getAddress().getFlatNumber())))
            .andExpect(jsonPath("user.address.postCode", is(sampleUser.getAddress().getPostCode())))
            .andExpect(jsonPath("user.address.city", is(sampleUser.getAddress().getCity())))
            .andExpect(jsonPath("user.coursesAsStudent", hasSize(0)))
            // two teacher courses
            // .andExpect(jsonPath("user.coursesAsTeacher", hasSize(1)))
            .andExpect(jsonPath("user.coursesAsTeacher[0].courseID", is(sampleTeacherCourse.getId())))
            .andExpect(jsonPath("user.coursesAsTeacher[0].language.id", is(sampleTeacherCourse.getLanguage().getId())))
            .andExpect(jsonPath("user.coursesAsTeacher[0].language.name", is(sampleTeacherCourse.getLanguage().getLanguageName("en"))))
            .andExpect(jsonPath("user.coursesAsTeacher[0].courseLevel", is(sampleTeacherCourse.getCourseLevel().getName())))
            .andExpect(jsonPath("user.coursesAsTeacher[0].courseType.courseTypeID", is(sampleTeacherCourse.getCourseType().getId())))
            .andExpect(jsonPath("user.coursesAsTeacher[0].courseType.name", is(sampleTeacherCourse.getCourseType().getCourseTypeName("en"))))
            .andExpect(jsonPath("user.coursesAsTeacher[0].teachers", hasSize(1)))
            .andExpect(jsonPath("user.coursesAsTeacher[0].teachers[0].userID", is(sampleCourseTeacher.getId())))
            .andExpect(jsonPath("user.coursesAsTeacher[0].teachers[0].name", is(sampleCourseTeacher.getFullName())))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testGetFieldInfo() throws Exception {
        Assert.fail();
    }

    @Test
    public void testEditAccount() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(0); // sampleUser1

        AccountJson editedAccount = new AccountJson();

        when(userCrudServiceMock.findUserByID(Mockito.any(String.class))).thenReturn(sampleUser);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        doNothing().when(userCrudServiceMock).updateUser(Mockito.any(User.class));

        this.mockMvc.perform(put(this.testedClassURI + '/' + sampleUser.getId())
            .contentType("application/json;charset=utf-8")
            .content(objectToJsonBytes(editedAccount))
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testEditField() throws Exception {
        Assert.fail();
    }

    @Test
    public void testDeactivateAccount() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(2); // sampleTeacher1

        when(userCrudServiceMock.findUserByID(Mockito.any(String.class))).thenReturn(sampleUser);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        doNothing().when(userCrudServiceMock).updateUser(Mockito.any(User.class));

        this.mockMvc.perform(put(this.testedClassURI + '/' + sampleUser.getId() + "/deactivate")
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testActivateAccount() throws Exception {
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(0); // sampleUser1

        when(userCrudServiceMock.findUserByID(Mockito.any(String.class))).thenReturn(sampleUser);
        when(labelProviderMock.getLabel(Mockito.any(String.class))).thenReturn(returnMessage);
        doNothing().when(userCrudServiceMock).updateUser(Mockito.any(User.class));

        this.mockMvc.perform(put(this.testedClassURI + '/' + sampleUser.getId() + "/activate")
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    public void testResetUserPassword() throws Exception {
        Assert.fail();
        /*
        String returnMessage = "";

        User sampleUser = this.testEnvironment.getUsers().get(0); // sampleUser1

        this.mockMvc.perform(put(this.testedClassURI + '/' + sampleUser.getId() + "/reset/password")
            .contentType("application/json;charset=utf-8")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=utf-8"))
            .andExpect(jsonPath("$.message", is(returnMessage)))
            .andExpect(jsonPath("$.success", is(true)));
        */
    }

    @Test
    public void testSearchUser() throws Exception {
        Assert.fail();
    }

}

/*

testGetAccountList - z testEnvironment:

{
   "success":true,
   "message":"",
   "users":{
      "users":[
         {
            "username":"abc",
            "firstName":"A",
            "lastName":"BC",
            "phone":[
               {
                  "phoneNumber":"552-222-222",
                  "phoneType":"MOBILE",
                  "phoneId":"3c704341-ada8-11e6-a506-005056c00001"
               }
            ],
            "address":{
               "street":"Sample street 2",
               "houseNumber":"80",
               "flatNumber":"5",
               "postCode":"80-520",
               "city":"Vdfs"
            },
            "userID":"3c968ff0-ada8-11e6-a506-005056c00001",
            "coursesAsStudent":[
               {
                  "course":{
                     "courseID":"3cc40890-ada8-11e6-a506-005056c00001",
                     "language":{
                        "id":"EN",
                        "name":"English"
                     },
                     "courseLevel":"A1",
                     "courseType":{
                        "courseTypeID":"3c47fac0-ada8-11e6-a506-005056c00001",
                        "name":"standard"
                     },
                     "teachers":[
                        {
                           "userID":"3cbcdca0-ada8-11e6-a506-005056c00001",
                           "name":"Teacher Teacher"
                        }
                     ]
                  },
                  "movedFrom":null,
                  "active":false,
                  "resignation":false
               }
            ],
            "coursesAsTeacher":[

            ],
            "email":"abc@samplemail.com"
         },
         {
            "username":"ramsay1",
            "firstName":"Ramsay",
            "lastName":"Bolton",
            "phone":[
               {
                  "phoneNumber":"666-666-666",
                  "phoneType":"MOBILE",
                  "phoneId":"3c4a44b0-ada8-11e6-a506-005056c00001"
               }
            ],
            "address":{
               "street":"Sample street 1",
               "houseNumber":"12a",
               "flatNumber":"5",
               "postCode":"12-511",
               "city":"Vdfs"
            },
            "userID":"3c704340-ada8-11e6-a506-005056c00001",
            "coursesAsStudent":[
               {
                  "course":{
                     "courseID":"3cc40890-ada8-11e6-a506-005056c00001",
                     "language":{
                        "id":"EN",
                        "name":"English"
                     },
                     "courseLevel":"A1",
                     "courseType":{
                        "courseTypeID":"3c47fac0-ada8-11e6-a506-005056c00001",
                        "name":"standard"
                     },
                     "teachers":[
                        {
                           "userID":"3cbcdca0-ada8-11e6-a506-005056c00001",
                           "name":"Teacher Teacher"
                        }
                     ]
                  },
                  "movedFrom":null,
                  "active":false,
                  "resignation":false
               }
            ],
            "coursesAsTeacher":[

            ],
            "email":"ramsay1@samplemail.com"
         },
         {
            "username":"teacher1",
            "firstName":"Teacher",
            "lastName":"Teacher",
            "phone":[
               {
                  "phoneNumber":"625-856-926",
                  "phoneType":"MOBILE",
                  "phoneId":"3c968ff1-ada8-11e6-a506-005056c00001"
               }
            ],
            "address":{
               "street":"Sample street 2",
               "houseNumber":"80",
               "flatNumber":"5",
               "postCode":"80-520",
               "city":"Vdfs"
            },
            "userID":"3cbcdca0-ada8-11e6-a506-005056c00001",
            "coursesAsStudent":[

            ],
            "coursesAsTeacher":[
               {
                  "courseID":"3cc42fa2-ada8-11e6-a506-005056c00001",
                  "language":{
                     "id":"EN",
                     "name":"English"
                  },
                  "courseLevel":"A1",
                  "courseType":{
                     "courseTypeID":"3c47fac0-ada8-11e6-a506-005056c00001",
                     "name":"standard"
                  },
                  "teachers":[
                     {
                        "userID":"3cbcdca0-ada8-11e6-a506-005056c00001",
                        "name":"Teacher Teacher"
                     }
                  ]
               },
               {
                  "courseID":"3cc40890-ada8-11e6-a506-005056c00001",
                  "language":{
                     "id":"EN",
                     "name":"English"
                  },
                  "courseLevel":"A1",
                  "courseType":{
                     "courseTypeID":"3c47fac0-ada8-11e6-a506-005056c00001",
                     "name":"standard"
                  },
                  "teachers":[
                     {
                        "userID":"3cbcdca0-ada8-11e6-a506-005056c00001",
                        "name":"Teacher Teacher"
                     }
                  ]
               }
            ],
            "email":"teacher1@samplemail.com"
         }
      ]
   }
}

*/