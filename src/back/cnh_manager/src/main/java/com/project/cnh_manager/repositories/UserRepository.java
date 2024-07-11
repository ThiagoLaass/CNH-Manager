package com.project.cnh_manager.repositories;

import com.project.cnh_manager.models.User;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByLogin(String login);
    User findUserById(UUID id);

    boolean existsById(@SuppressWarnings("null") UUID id);
}
