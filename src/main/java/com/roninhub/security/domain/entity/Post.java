package com.roninhub.security.domain.entity;

import com.roninhub.security.domain.enumeration.PostType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Post {
    private Long id;

    private String title;

    private String content;

    private String summary;

    private String avatarUrl;

    private String slug;

    private PostType type;
}
