package org.smart4j.framework.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    public static <T> String toJson(T obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("obj class: " + obj.getClass().getName() + ", obj value: " + obj.toString());
            throw new RuntimeException("Convert POJO to JSON failure", e);
        }
    }

}
