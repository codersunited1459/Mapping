package org.mapping.onetoone.service;


import org.mapping.onetoone.dto.CreateUserRequest;
import org.mapping.onetoone.dto.ProfileRequest;
import org.mapping.onetoone.dto.UpdateUserRequest;
import org.mapping.onetoone.dto.UserResponse;
import org.mapping.onetoone.entities.Profile;
import org.mapping.onetoone.entities.User;
import org.mapping.onetoone.exception.ResourceNotFoundException;
import org.mapping.onetoone.mapper.UserMapper;
import org.mapping.onetoone.repository.ProfileRepository;
import org.mapping.onetoone.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public UserService(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }
    @Transactional
    public UserResponse createUser(CreateUserRequest req) {

        User user = new User(req.getName(), req.getEmail());

        if (req.getProfile() != null) {
            Profile profile = new Profile(
                    req.getProfile().getPhone(),
                    req.getProfile().getAddress()
            );

            user.setProfile(profile); // ⭐ important
        }


        User usr = userRepository.save(user);

        // convert entity to DTO
        return UserMapper.toResponse(usr);
    }

    @Transactional(readOnly = true)
    public UserResponse getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
        return UserMapper.toResponse(user);
    }

    @Transactional
    public UserResponse updateUser(Long userId, UpdateUserRequest req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        if (req.getName() != null) {
            user.setName(req.getName());
        }

        if (req.getProfile() != null) {
            applyProfileUpdate(user, req.getProfile());
        }

        user = userRepository.save(user);
        return UserMapper.toResponse(user);
    }

    private void applyProfileUpdate(User user, ProfileRequest profileReq) {
        Profile profile = user.getProfile();

        // If profile doesn't exist, create it (still persisted due to cascade)
        if (profile == null) {
            profile = new Profile(profileReq.getPhone(), profileReq.getAddress());
            user.setProfile(profile);
            return;
        }

        // Otherwise update existing
        if (profileReq.getPhone() != null) {
            profile.setPhone(profileReq.getPhone());
        }
        if (profileReq.getAddress() != null) {
            profile.setAddress(profileReq.getAddress());
        }
    }

    @Transactional
    public void removeProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        // orphanRemoval=true => profile row will be deleted
        user.setProfile(null);
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found: " + userId);
        }
        // cascade + orphanRemoval: profile also deleted
        userRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserByProfileId(Long profileId) {

        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found: " + profileId));

        if (profile.getUser() == null) {
            // This shouldn't happen in a consistent 1-1 mapping, but safe check
            throw new ResourceNotFoundException("User not found for profileId: " + profileId);
        }

        return UserMapper.toResponse(profile.getUser());
    }

}