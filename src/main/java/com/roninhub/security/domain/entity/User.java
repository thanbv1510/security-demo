package com.roninhub.security.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class User {
    private Long Id;

    private String username;

    private String password;

    private Set<Role> roles;
}
