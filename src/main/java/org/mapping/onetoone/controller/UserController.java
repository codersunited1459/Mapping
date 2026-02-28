package org.mapping.onetoone.controller;

import org.mapping.onetoone.dto.CreateUserRequest;
import org.mapping.onetoone.dto.UpdateUserRequest;
import org.mapping.onetoone.dto.UserResponse;
import org.mapping.onetoone.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody CreateUserRequest req) {
        return userService.createUser(req);
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable Long userId,
                                   @RequestBody UpdateUserRequest req) {
        return userService.updateUser(userId, req);
    }

    @DeleteMapping("/{userId}/profile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProfile(@PathVariable Long userId) {
        userService.removeProfile(userId);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/{profileId}/user")
    public UserResponse getUserByProfileId(@PathVariable Long profileId) {
        return userService.getUserByProfileId(profileId);
    }
}