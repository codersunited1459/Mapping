package org.mapping.onetoone.dto;

public class UserResponse {
    private Long id;
    private String name;
    private String email;

    private Long profileId;
    private String phone;
    private String address;

    public UserResponse() { }

    public UserResponse(Long id, String name, String email,
                        Long profileId, String phone, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profileId = profileId;
        this.phone = phone;
        this.address = address;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Long getProfileId() { return profileId; }
    public void setProfileId(Long profileId) { this.profileId = profileId; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}