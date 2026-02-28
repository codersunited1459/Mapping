package org.mapping.onetoone.repository;

import org.mapping.onetoone.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    @Query("select p from Profile p join fetch p.user where p.id = :id")
    Optional<Profile> findByIdWithUser(Long id);
}