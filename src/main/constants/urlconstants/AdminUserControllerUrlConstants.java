package main.constants.urlconstants;

public class AdminUserControllerUrlConstants {

    public static final String CLASS_URL = GlobalUrlConstants.GLOBAL_API_URL + "/admin/user";

    public static final String ACCOUNT_LIST = "/list";

    public static final String ADD_ACCOUNT = "";

    public static final String SEARCH_USER = "/search";

    public static final String ACCOUNT_INFO = "/{userID}";

    public static final String ACCOUNT_INFO_FIELD = "/{userID}/{fieldName}";

    public static final String EDIT_ACCOUNT = "/{userID}";

    public static final String EDIT_ACCOUNT_FIELD = "/{userID}/{fieldName}";

    public static final String DEACTIVATE_ACCOUNT = "/{userID}/deactivate";

    public static final String ACTIVATE_ACCOUNT = "/{userID}/activate";

    public static final String RESET_PASSWORD = "/{userID}/reset/password";

}
