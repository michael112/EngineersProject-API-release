package main.model;

import java.util.regex.Pattern;

import com.eaio.uuid.UUID;

public class UuidGenerator extends StringIdGenerator {

    @Override
    public String getStringId() {
        return UuidGenerator.newUUID();
    }

    public static String newUUID() {
        return new UUID().toString();
    }

    public static boolean isUUID(String text) {
        Pattern p = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f\u200C\u200B]{4}-[0-9a-f]{12}$");
        return p.matcher(text).matches();
    }
}
