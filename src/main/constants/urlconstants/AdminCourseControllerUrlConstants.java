package main.constants.urlconstants;

public class AdminCourseControllerUrlConstants {

    public static final String CLASS_URL = GlobalUrlConstants.GLOBAL_API_URL + "/admin/course";

    public static final String COURSE_LIST = "/list";

    public static final String COURSE_INFO = "/{courseID}";

    public static final String AVAILABLE_CREATING_COURSE_DATA = "";

    public static final String ADD_COURSE = "";

    public static final String EDIT_COURSE = "/{courseID}";

    public static final String EDIT_COURSE_LANGUAGE = "/{courseID}/language";

    public static final String EDIT_COURSE_TYPE = "/{courseID}/coursetype";

    public static final String EDIT_COURSE_LEVEL = "/{courseID}/level";

    public static final String EDIT_COURSE_ACTIVITY = "/{courseID}/activity";

    public static final String EDIT_COURSE_DAYS = "/{courseID}/days";

    public static final String EDIT_COURSE_TEACHER = "/{courseID}/teacher/{teacherID}";

    public static final String EDIT_COURSE_ADD_TEACHER = "/{courseID}/teacher/add/{teacherID}";

    public static final String EDIT_COURSE_REMOVE_TEACHER = "/{courseID}/teacher/remove/{teacherID}";

    public static final String EDIT_COURSE_MAX_STUDENTS = "/{courseID}/max/students";

    public static final String EDIT_COURSE_PRICE = "/{courseID}/price";

    public static final String REMOVE_COURSE = "/{courseID}";

}
