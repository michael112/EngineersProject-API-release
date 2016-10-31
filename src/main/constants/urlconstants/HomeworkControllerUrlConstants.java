package main.constants.urlconstants;

public class HomeworkControllerUrlConstants {

    public static final String CLASS_URL = GlobalUrlConstants.GLOBAL_API_URL + "/course/{courseID}/homework";

    public static final String HOMEWORK_LIST = "/list";

    public static final String HOMEWORK_INFO = "/{homeworkID}";

    public static final String SEND_SOLUTION = "/{homeworkID}/solve";

    public static final String ADD_HOMEWORK = "";

    public static final String EDIT_HOMEWORK_TITLE = "/{homeworkID}/title";

    public static final String EDIT_HOMEWORK_DATE = "/{homeworkID}/date";

    public static final String EDIT_HOMEWORK_DESCRIPTION = "{homeworkID}/description";

    public static final String EDIT_HOMEWORK_ADD_ATTACHEMENT = "/{homeworkID}/add/attachement";

    public static final String EDIT_HOMEWORK_REMOVE_ATTACHEMENT = "/{homeworkID}/remove/attachement/{attachementID}";

    public static final String REMOVE_HOMEWORK = "/{homeworkID}";

}
