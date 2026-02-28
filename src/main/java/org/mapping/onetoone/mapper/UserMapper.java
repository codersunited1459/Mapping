package org.mapping.onetoone.mapper;

import org.mapping.onetoone.dto.UserResponse;
import org.mapping.onetoone.entities.Profile;
import org.mapping.onetoone.entities.User;

public class UserMapper {

    private UserMapper() { }

    public static UserResponse toResponse(User user) {
        Profile p = user.getProfile();
        if (p == null) {
            return new UserResponse(user.getId(), user.getName(), user.getEmail(),
                    null, null, null);
        }
        return new UserResponse(user.getId(), user.getName(), user.getEmail(),
                p.getId(), p.getPhone(), p.getAddress());
    }
}