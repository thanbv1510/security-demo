package com.roninhub.security.util;

import com.sun.net.httpserver.HttpExchange;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HttpUtil {

    public static <T> T getRequestBody(final HttpExchange exchange, Class<T> tClass) {
        var stringBuilder = new StringBuilder();
        var inputStream = exchange.getRequestBody();

        try {
            int value;
            while ((value = inputStream.read()) != -1) {
                stringBuilder.append((char) value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ObjectMapperUtil.convertJsonToObject(stringBuilder.toString(), tClass).orElse(null);
    }
}
