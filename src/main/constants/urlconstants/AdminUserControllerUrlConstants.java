package main.constants.urlconstants;

public class AdminUserControllerUrlConstants {

    public static final String CLASS_URL = GlobalUrlConstants.GLOBAL_API_URL + "/admin/user";

    public static final String ACCOUNT_LIST = "/list";

    public static final String ADD_ACCOUNT = "";

    public static final String SEARCH_USER = "/search";

    public static final String ACCOUNT_INFO = "/{userID}";

    public static final String ACCOUNT_INFO_USERNAME = "/{userID}/username";

    public static final String ACCOUNT_INFO_NAME = "/{userID}/name";

    public static final String ACCOUNT_INFO_EMAIL = "/{userID}/email";

    public static final String ACCOUNT_INFO_PHONE_LIST = "/{userID}/phone";

    public static final String ACCOUNT_INFO_PHONE = "/{userID}/phone/{phoneID}";

    public static final String ACCOUNT_INFO_ADDRESS = "/{userID}/address";

    public static final String EDIT_ACCOUNT = "/{userID}";

    public static final String EDIT_ACCOUNT_USERNAME = "/{userID}/username";

    public static final String EDIT_ACCOUNT_NAME = "/{userID}/name";

    public static final String EDIT_ACCOUNT_EMAIL = "/{userID}/email";

    public static final String EDIT_ACCOUNT_PHONE = "/{userID}/phone/{phoneID}";

    public static final String EDIT_ACCOUNT_ADD_PHONE = "/{userID}/phone";

    public static final String EDIT_ACCOUNT_REMOVE_PHONE = "/{userID}/phone/{phoneID}";

    public static final String EDIT_ACCOUNT_ADDRESS = "/{userID}/address";

    public static final String DEACTIVATE_ACCOUNT = "/{userID}/deactivate";

    public static final String ACTIVATE_ACCOUNT = "/{userID}/activate";

    public static final String RESET_PASSWORD = "/{userID}/reset/password";

}
