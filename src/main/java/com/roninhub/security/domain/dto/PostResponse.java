package com.roninhub.security.domain.dto;

import com.roninhub.security.domain.entity.Post;
import com.roninhub.security.domain.enumeration.PostType;
import com.roninhub.security.util.ObjectMapperUtil;
import lombok.Data;

@Data
public class PostResponse {
    private Long id;

    private String title;

    private String content;

    private String summary;

    private String avatarUrl;

    private String slug;

    private PostType type;

    public static PostResponse of(final Post post) {
        return ObjectMapperUtil.OBJECT_MAPPER.convertValue(post, PostResponse.class);
    }
}
