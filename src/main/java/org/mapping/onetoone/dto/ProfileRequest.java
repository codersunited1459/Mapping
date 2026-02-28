package org.mapping.onetoone.dto;

public class ProfileRequest {
    private String phone;
    private String address;

    public ProfileRequest() { }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}