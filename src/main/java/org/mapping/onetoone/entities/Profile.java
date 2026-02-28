package org.mapping.onetoone.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;
    private String address;

    // INVERSE SIDE
    @OneToOne(mappedBy = "profile")
    private User user;

    public Profile() { }

    public Profile(String phone, String address) {
        this.phone = phone;
        this.address = address;
    }

    // getters setters
    public Long getId() { return id; }

    public String getPhone() { return phone; }
    public String getAddress() { return address; }

    public User getUser() { return user; }

    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }
    public void setUser(User user) { this.user = user; }
}