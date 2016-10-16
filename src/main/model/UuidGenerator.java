package main.model;

import java.util.regex.Pattern;

import com.eaio.uuid.UUID;

import main.constants.validationconstants.ValidationConstants;

public class UuidGenerator extends StringIdGenerator {

    @Override
    public String getStringId() {
        return UuidGenerator.newUUID();
    }

    public static String newUUID() {
        return new UUID().toString();
    }

    public static boolean isUUID(String text) {
        Pattern p = Pattern.compile(ValidationConstants.UUID_REGEX);
        return p.matcher(text).matches();
    }
}
