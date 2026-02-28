package org.mapping.onetoone.dto;

public class UpdateUserRequest {
    private String name;
    private ProfileRequest profile; // optional

    public UpdateUserRequest() { }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ProfileRequest getProfile() { return profile; }
    public void setProfile(ProfileRequest profile) { this.profile = profile; }
}