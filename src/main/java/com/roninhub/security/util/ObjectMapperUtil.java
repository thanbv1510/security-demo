package com.roninhub.security.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class ObjectMapperUtil {
    public static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
    }

    public <T> String convertObjectToString(T object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            return "";
        }
    }

    public <T> Optional<T> convertJsonToObject(String json, Class<T> tClass) {
        try {
            return Optional.ofNullable(OBJECT_MAPPER.readValue(json, tClass));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
