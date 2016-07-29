package main.model;

import com.eaio.uuid.UUID;

public class UuidGenerator extends StringIdGenerator {

    @Override
    public String getStringId() {
        return UuidGenerator.newUUID();
    }

    public static String newUUID() {
        return new UUID().toString();
    }
}
