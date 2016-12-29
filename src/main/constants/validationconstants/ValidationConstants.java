package main.constants.validationconstants;

public class ValidationConstants {

    public static final String UUID_REGEX = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

    public static final String COURSE_LEVEL_REGEX = "[abcABC][12]";

    public static final String PUNKOWA_SZKOLNA_REGEX = "^(PUNKTOWA|SZKOLNA)$";

    public static final String PHONE_REGEX = "^MOBILE|LANDLINE$";

    public static final String DATE_REGEX = "(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-\\d{4}";

    public static final int PHONE_NUMBER_MIN = 4;
    public static final int PHONE_NUMBER_MAX = 20;

}
