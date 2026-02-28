package org.mapping.onetoone.dto;

public class CreateUserRequest {
    private String name;
    private String email;
    private ProfileRequest profile; // optional

    public CreateUserRequest() { }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public ProfileRequest getProfile() { return profile; }
    public void setProfile(ProfileRequest profile) { this.profile = profile; }
}