package com.roninhub.security.domain.dto;

import com.roninhub.security.util.ObjectMapperUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommonResponse<T> {
    private int code;

    private String err;

    private T data;

    public static CommonResponse<?> of(final int code, final String err) {
        return new CommonResponse<>(code, err, null);
    }

    public static <T> CommonResponse<T> of(final int code, final String err, T data) {
        return new CommonResponse<>(code, err, data);
    }

    @Override
    public String toString() {
        return ObjectMapperUtil.convertObjectToString(this);
    }
}
