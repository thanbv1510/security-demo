package com.roninhub.security.service;

import com.roninhub.security.domain.dto.CommonResponse;
import com.roninhub.security.domain.dto.PostResponse;
import com.roninhub.security.domain.entity.Post;
import com.roninhub.security.domain.enumeration.PostType;

import java.util.List;
import java.util.Map;

public class PostService {
    // Fake data
    public static final Map<Long, Post> POST_DATA = Map.of(
            1L, new Post(1L, "title1", "content1", "summary1", "avatarUrl1", "slug1", PostType.FREE),
            2L, new Post(2L, "title2", "content2", "summary2", "avatarUrl2", "slug2", PostType.FREE),
            3L, new Post(3L, "title3", "content3", "summary3", "avatarUrl3", "slug3", PostType.PREMIUM),
            4L, new Post(4L, "title4", "content4", "summary4", "avatarUrl4", "slug4", PostType.PREMIUM),
            5L, new Post(5L, "title5", "content5", "summary5", "avatarUrl5", "slug5", PostType.PREMIUM)
    );

    public CommonResponse<List<PostResponse>> getAllPostByType(final PostType type) {
        var posts = POST_DATA.values().stream()
                .filter(post -> post.getType().equals(type))
                .map(PostResponse::of)
                .toList();

        return CommonResponse.of(200, "success", posts);
    }
}
