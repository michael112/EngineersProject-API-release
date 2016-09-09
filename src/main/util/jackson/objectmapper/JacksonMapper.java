package main.util.jackson.objectmapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.SerializationFeature;

public class JacksonMapper extends ObjectMapper {

    public JacksonMapper() {
        super();
        this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

}
