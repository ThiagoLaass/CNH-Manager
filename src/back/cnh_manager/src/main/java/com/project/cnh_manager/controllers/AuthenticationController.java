package com.project.cnh_manager.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.cnh_manager.configs.TokenService;
import com.project.cnh_manager.dto.AuthenticationDTO;
import com.project.cnh_manager.dto.LoginResponseDTO;
import com.project.cnh_manager.dto.RegisterDTO;
import com.project.cnh_manager.models.User;
import com.project.cnh_manager.models.UserRole;
import com.project.cnh_manager.repositories.UserRepository;
import com.project.cnh_manager.services.AuthenticationService;
import com.project.cnh_manager.services.CargaHorariaService;
import com.project.cnh_manager.services.UserService;

import jakarta.validation.Valid;


@RestController()
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CargaHorariaService cargaHorariaService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);
        var id = ((User) auth.getPrincipal()).getId();
        var role = ((User) auth.getPrincipal()).getRole();
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token, id, role));
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(authenticationService.findAll());
    }

    @GetMapping("/user/all/instrutor")
    public ResponseEntity<List<User>> getAllInstrutores(){
        return ResponseEntity.ok(authenticationService.findAllInstrutores());
    }
    

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data) {
        if (this.repository.findByLogin(data.login()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), data.email(), encryptedPassword, data.role());
        
        this.repository.save(newUser);

        System.out.println(newUser.getRole());
        if(newUser.getRole() == UserRole.USER){
            cargaHorariaService.inicializarCargaHorariaDoAluno(newUser.getId());
        }  
  
        return ResponseEntity.ok().build();
    }

    @PutMapping("/setinstrutor/{instrutorId}/{userId}")
    public ResponseEntity<?> putHorario(@PathVariable UUID instrutorId, @PathVariable UUID userId) {
        User alunoAtualizado = userService.setInstrutor(instrutorId, userId);
        return ResponseEntity.ok().body(alunoAtualizado);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findById(id));
    }

}