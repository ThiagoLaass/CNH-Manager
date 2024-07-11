package com.project.cnh_manager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.project.cnh_manager.models.Prova;
import com.project.cnh_manager.models.TipoProva;
import com.project.cnh_manager.models.User;
import com.project.cnh_manager.models.UserRole;
import com.project.cnh_manager.repositories.ProvaRepository;
import com.project.cnh_manager.repositories.TipoProvaRepository;
import com.project.cnh_manager.repositories.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProvaRepositoryTest {

     @Autowired
    private ProvaRepository provaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TipoProvaRepository tipoProvaRepository;

    @Test
    public void testSaveProva() {
        // Crie e salve as entidades necessárias
        User user = new User();
        user.setEmail("testUser@email.com");
        user.setLogin("userTest");
        user.setPassword("teste");
        user.setRole(UserRole.USER);
        user = userRepository.save(user);

        TipoProva tipoProva = new TipoProva();
        tipoProva.setNome("Prova Prática");
        tipoProva = tipoProvaRepository.save(tipoProva);

        // Crie a entidade Prova
        Prova prova = new Prova();
        prova.setUser(user);
        prova.setTipoProva(tipoProva);
        
        // Salve a Prova no banco de dados
        Prova savedProva = provaRepository.save(prova);

        // Verifique se a Prova foi salva corretamente
        assertThat(savedProva).isNotNull();
        assertThat(savedProva.getId()).isNotNull();
        assertThat(savedProva.getUser()).isEqualTo(user);
        assertThat(savedProva.getTipoProva()).isEqualTo(tipoProva);
    }
    
}
