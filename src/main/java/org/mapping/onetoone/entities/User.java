package org.mapping.onetoone.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    // OWNING SIDE
    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "profile_id", unique = true)
    private Profile profile;

    public User() { }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // ⭐ VERY IMPORTANT HELPER METHOD
    public void setProfile(Profile profile) {
        this.profile = profile;

        // maintain both sides of relation
        if (profile != null) {
            profile.setUser(this);
        }
    }

    public void removeProfile() {
        if (this.profile != null) {
            this.profile.setUser(null);
            this.profile = null;
        }
    }

    // getters setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Profile getProfile() { return profile; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
}