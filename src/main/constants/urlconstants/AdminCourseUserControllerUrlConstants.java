package main.constants.urlconstants;

public class AdminCourseUserControllerUrlConstants {

    public static final String CLASS_URL = GlobalUrlConstants.GLOBAL_API_URL + "/admin/course/{courseID}/user";

    public static final String COURSE_USER_LIST = "/list";

    public static final String ADD_COURSE_USER = "/{userID}";

    public static final String SUSPEND_COURSE_USER = "/{userID}/suspend";

    public static final String ACTIVATE_COURSE_USER = "/{userID}/activate";

    public static final String REMOVE_COURSE_USER = "/{userID}/remove";

    public static final String MOVE_USER_GROUP = "/{userID}/change/{newCourseID}";

    public static final String APPLY_USER_CHANGES = "/{userID}/applyUserChanges";

    public static final String REVERT_USER_CHANGES = "/{userID}/revertUserChanges";

}
