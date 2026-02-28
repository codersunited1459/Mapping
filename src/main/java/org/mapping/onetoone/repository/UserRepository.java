package org.mapping.onetoone.repository;

import org.mapping.onetoone.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}