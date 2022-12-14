package main.constants.urlconstants;

public class CourseControllerUrlConstants {

    public static final String CLASS_URL = GlobalUrlConstants.GLOBAL_API_URL + "/course";

    public static final String COURSE_INFO = "/{id}";

    public static final String COURSE_STUDENT_LIST = "/{id}/list";

    public static final String COURSE_SHOW_AVAILABLE_INFO_TO_SIGNUP = "/signup";

    public static final String COURSE_SEARCH_COURSES = "/signup";

    public static final String SIGNUP_TO_COURSE = "/signup/{courseID}";

    public static final String CHANGE_GROUP_FORM = "/{courseID}/change";

    public static final String CHANGE_GROUP = "/{oldCourseID}/change/{newCourseID}";

    public static final String RESIGNATION_FORM = "/{courseID}/resignation";

    public static final String RESIGNATION_CONFIRM = "/{courseID}/resignation";

    public static final String COURSE_MEMBERSHIP_TYPE = "/{courseID}/coursemembershiptype";

}
