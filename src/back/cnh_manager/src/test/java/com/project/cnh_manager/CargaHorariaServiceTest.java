package com.project.cnh_manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalTime;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.cnh_manager.models.Aula;
import com.project.cnh_manager.models.CargaHorariaConcluida;
import com.project.cnh_manager.models.HorarioAulaPratica;
import com.project.cnh_manager.models.User;
import com.project.cnh_manager.services.CargaHorariaService;

@SpringBootTest
public class CargaHorariaServiceTest {
    
    @Autowired
    private CargaHorariaService cargaHorariaService;

    @Test
    public void testSetHours() {
        User user = new User();

        HorarioAulaPratica horario = new HorarioAulaPratica();
        Aula aula = new Aula();
        horario.setHoraInicio(LocalTime.of(8, 0));
        horario.setHoraFim(LocalTime.of(10, 0));

        cargaHorariaService.calculateHours(horario, user);

        List<CargaHorariaConcluida> cargasConcluidas = user.getCargaHorariaConcluida();
        CargaHorariaConcluida cargaPratica = cargasConcluidas.stream()
                .filter(cargaHoraria -> cargaHoraria.getAula().equals(aula))
                .findFirst()
                .orElse(null);

        assertNotNull(cargaPratica);
        assertEquals(2, cargaPratica);
    }

    @Test
    public void testSetHorasPratica(){
        User user = new User();
        Integer horas = 10;
        Aula aula = new Aula();
        aula.setNome("Prática");
        cargaHorariaService.setHorasPratica(horas, user);

        List<CargaHorariaConcluida> cargasConcluidas = user.getCargaHorariaConcluida();
        CargaHorariaConcluida cargaPratica = cargasConcluidas.stream()
                .filter(cargaHoraria -> cargaHoraria.getAula().equals(aula))
                .findFirst()
                .orElse(null);

        assertNotNull(cargaPratica);
        assertEquals(10, cargaPratica);
    }
}