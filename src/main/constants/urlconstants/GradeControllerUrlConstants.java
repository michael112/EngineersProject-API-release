package main.constants.urlconstants;

public class GradeControllerUrlConstants {

    public static final String CLASS_URL = GlobalUrlConstants.GLOBAL_API_URL + "/course/{courseID}/grade";

    public static final String GRADE_LIST = "/list";

    public static final String GRADE_INFO = "/{gradeID}";

    public static final String ADD_GRADE = "";

    public static final String ADD_STUDENT_GRADE = "/{gradeID}/student";

    public static final String EDIT_GRADE = "/{gradeID}";

    public static final String EDIT_GRADE_INFO = EDIT_GRADE + "/info";

    public static final String EDIT_GRADE_POINTS = EDIT_GRADE + "/points";

    public static final String EDIT_GRADE_SCALE = EDIT_GRADE + "/scale";

    public static final String EDIT_STUDENT_GRADE = "/{gradeID}/student";

}
