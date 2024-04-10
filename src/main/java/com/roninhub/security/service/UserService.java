package com.roninhub.security.service;

import com.roninhub.security.domain.dto.CommonResponse;
import com.roninhub.security.domain.dto.LoginRequest;
import com.roninhub.security.domain.dto.LoginResponse;
import com.roninhub.security.domain.entity.Permission;
import com.roninhub.security.domain.entity.Role;
import com.roninhub.security.domain.entity.User;
import com.roninhub.security.domain.enumeration.PermissionName;
import com.roninhub.security.domain.enumeration.RoleName;
import com.roninhub.security.util.JwtUtils;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class UserService {
    public static final Map<String, User> USER_DATA;


    // Fake data
    static {
        // Permissions
        var postFreeView = new Permission(1L, PermissionName.POST_FREE_VIEW);
        var postFreeEdit = new Permission(2L, PermissionName.POST_FREE_EDIT);
        var postPremiumView = new Permission(3L, PermissionName.POST_PREMIUM_VIEW);
        var postPremiumEdit = new Permission(4L, PermissionName.POST_PREMIUM_EDIT);

        // Roles
        var adminRole = new Role(1L, RoleName.ADMIN, Set.of(postFreeView, postFreeEdit, postPremiumView, postPremiumEdit));
        var editorRole = new Role(2L, RoleName.EDITOR, Set.of(postFreeView, postFreeEdit, postPremiumView, postPremiumEdit));
        var userFreeRole = new Role(2L, RoleName.USER_FREE, Set.of(postFreeView));
        var userPremiumRole = new Role(2L, RoleName.USER_PREMIUM, Set.of(postFreeView, postPremiumView));
        var anomymousRole = new Role(2L, RoleName.ANONYMOUS, Set.of(postFreeView));

        // Users
        var roninFree1 = new User(1L, "roninFree1", "123456", Set.of(userFreeRole, anomymousRole));
        var roninFree2 = new User(2L, "roninFree2", "123456", Set.of(userFreeRole));

        var roninPremium1 = new User(3L, "roninPremium1", "123456", Set.of(userFreeRole, anomymousRole, userPremiumRole));
        var roninPremium2 = new User(4L, "roninPremium2", "123456", Set.of(userFreeRole, userPremiumRole));

        var roninEditor = new User(5L, "roninEditor", "123456", Set.of(userFreeRole, anomymousRole, userPremiumRole, editorRole));

        var roninAdmin = new User(6L, "roninAdmin", "123456", Set.of(adminRole));

        USER_DATA = Map.of(
                "roninFree1", roninFree1,
                "roninFree2", roninFree2,
                "roninPremium1", roninPremium1,
                "roninPremium2", roninPremium2,
                "roninEditor", roninEditor,
                "roninAdmin", roninAdmin
        );
    }

    public CommonResponse<?> login(final @NonNull LoginRequest request) {
        //1. Get user by username in database
        var user = USER_DATA.get(request.getUsername());
        if (Objects.isNull(user)) {
            return CommonResponse.of(404, "User %s not found".formatted(request.getUsername()));
        }

        // 2. verify password
        if (!user.getPassword().equals(request.getPassword())) {
            return CommonResponse.of(401, "Invalid password");
        }

        // 3. Generate jwt for user
        String jwt = JwtUtils.generateJwtToken(user.getUsername());

        return CommonResponse.of(200, "success", LoginResponse.of(user, jwt));
    }


    public boolean isHasAnyPermission(String username, PermissionName... permissions) {
        var user = USER_DATA.get(username);
        if (Objects.isNull(user)) {
            return false;
        }

        var userPermissions = user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(Permission::getName)
                .collect(Collectors.toSet());

        return Arrays.stream(permissions).anyMatch(userPermissions::contains);
    }
}
