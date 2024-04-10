package com.roninhub.security.domain.entity;

import com.roninhub.security.domain.enumeration.PermissionName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Permission {
    private Long id;

    private PermissionName name;
}
