package com.project.cnh_manager.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.project.cnh_manager.models.Pagamento;
import com.project.cnh_manager.models.User;
import com.project.cnh_manager.repositories.UserRepository;
import com.project.cnh_manager.services.exceptions.AuthorizationException;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public static User authenticated() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            throw new AuthorizationException("Acesso negado");
        }
        return user;
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    public User setInstrutor(UUID instrutorId, UUID userId){
        User aluno = userRepository.findById(userId).orElse(null);
        aluno.setInstrutorId(instrutorId);
        return userRepository.save(aluno); 
    }

    public User removePagamento(User user, Pagamento pagamento){
        user.getPagamentos().remove(pagamento);
        return userRepository.save(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
}