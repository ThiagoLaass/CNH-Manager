package com.project.cnh_manager;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.cnh_manager.models.HorarioAulaPratica;
import com.project.cnh_manager.models.User;
import com.project.cnh_manager.services.HorarioPraticaService;

@SpringBootTest
public class HorarioPraticaServiceTest {

    HorarioPraticaService horarioPraticaService;

    @Test
    public void testConfirm(){
        UUID codHorario = UUID.randomUUID();
        HorarioAulaPratica horario = new HorarioAulaPratica();
        horario.setCodHorario(codHorario);
        horarioPraticaService.confirm(codHorario);
        
        assertEquals(true, horario.isAguardandoAprovacao());
    }

    @Test
    public void testCancel(){
        UUID codHorario = UUID.randomUUID();
        HorarioAulaPratica horario = new HorarioAulaPratica();
        horario.setCodHorario(codHorario);
        horarioPraticaService.cancel(codHorario);
        
        assertEquals(true, horario.isStatusAberto());
        assertEquals(null, horario.getAluno());
    }

    @Test
    public void testSetUserInstrutor(){
        UUID codHorario = UUID.randomUUID();
        HorarioAulaPratica horario = new HorarioAulaPratica();
        horario.setCodHorario(codHorario);
        User aluno = new User();
        horarioPraticaService.setUserInstrutor(horario, codHorario, aluno);
        
        assertEquals(true, horario.isStatusAberto());
        assertEquals(aluno, horario.getAluno());
    }
}