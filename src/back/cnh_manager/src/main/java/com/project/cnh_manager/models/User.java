package com.project.cnh_manager.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class User implements UserDetails{
    public interface CreateUser {
    }

    public interface UpdateUser {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_usuario", unique = true, nullable = false)
    private UUID id;

    @Column(name = "email", length = 100, nullable = false)
    @NotNull(groups = {CreateUser.class, UpdateUser.class})
    @NotEmpty(groups = {CreateUser.class, UpdateUser.class})
    @Size(groups = CreateUser.class, min = 2, max = 100)
    private String email;

    @Column(name = "login", length = 100, nullable = false)
    @NotNull(groups = {CreateUser.class, UpdateUser.class})
    @NotEmpty(groups = {CreateUser.class, UpdateUser.class})
    @Size(groups = CreateUser.class, min = 2, max = 100)
    private String login;


    @Column(name = "senha", length = 100, nullable = false)
    @NotNull(groups = {CreateUser.class, UpdateUser.class})
    @NotEmpty(groups = {CreateUser.class, UpdateUser.class})
    private String password;

    @Column(name = "id_instrutor", nullable = true)
    private UUID instrutorId;

    @Column(name = "role", length = 100, nullable = false)
    private UserRole role;

    @Column(name = "quant_aulas_adicionais", columnDefinition = "INT DEFAULT 0")
    private int quantAulasAdicionais;

    @Column(name = "cnhAprovada", columnDefinition = "BOOLEAN DEFAULT false")
    @NotNull
    private boolean cnhAprovada;

    @OneToMany(mappedBy = "user")
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();

    @OneToMany(mappedBy = "user")
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<Prova> provas = new ArrayList<Prova>();

    @OneToMany(mappedBy = "user")
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<ProvaSolicitacao> solicitacoesProva = new ArrayList<ProvaSolicitacao>();

    @OneToMany(mappedBy = "user")
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<CargaHorariaConcluida> cargaHorariaConcluida = new ArrayList<CargaHorariaConcluida>();

    @OneToMany(mappedBy = "user") // @OneToMany(mappedBy = "pagamento")
    @JsonProperty(access = Access.WRITE_ONLY)
    private List<Pagamento> pagamentos = new ArrayList<Pagamento>();


    @Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    return switch (this.role) {
        case ADMIN -> List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        case INSTRUTOR -> List.of(new SimpleGrantedAuthority("ROLE_INSTRUTOR"), new SimpleGrantedAuthority("ROLE_USER"));
        case USER -> List.of ( new SimpleGrantedAuthority("ROLE_USER"));
        default -> List.of();
    };
}


    public User(String login, String email, String password, UserRole role) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.role = role;
    }


    @Override
    public String getUsername() {
        return login;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }

}
