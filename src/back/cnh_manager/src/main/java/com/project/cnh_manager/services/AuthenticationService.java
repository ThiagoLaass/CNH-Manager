package com.project.cnh_manager.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.cnh_manager.models.User;
import com.project.cnh_manager.models.UserRole;
import com.project.cnh_manager.repositories.UserRepository;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll().stream().toList();
    }

    public List<User> findAllInstrutores() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole() == UserRole.INSTRUTOR)
                .toList();
    }
}
