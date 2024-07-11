package com.project.cnh_manager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.cnh_manager.models.Pagamento;
import com.project.cnh_manager.models.User;
import com.project.cnh_manager.services.UserService;

@SpringBootTest
public class UserServiceTest {
    private UserService userService;


    @Test
    public void testSetInstrutor(){
        User aluno = new User();
        User instrutor = new User();

        UUID alunoId = UUID.randomUUID();
        UUID instrutorId = UUID.randomUUID();
        aluno.setId(alunoId);
        instrutor.setId(instrutorId);

        userService.setInstrutor(aluno.getId(), instrutor.getId());

        assertEquals(aluno.getInstrutorId(), instrutorId);
    }

    @Test
    public void testRemovePagamento(){
        User aluno = new User();
        Pagamento p = new Pagamento();
       
        UUID alunoId = UUID.randomUUID();
        aluno.setId(alunoId);
        aluno.setPagamentos(Arrays.asList(p));
        
        userService.removePagamento(aluno, p);
    
        assertEquals(aluno.getPagamentos(), null);
    }

    @Test
    public void testFindByLogin(){
        User user = new User();
        user.setLogin("thiago");
        userService.findByLogin("thiago");
        assertEquals(user.getLogin(), "thiago");
    }

}