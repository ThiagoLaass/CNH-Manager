package com.project.cnh_manager.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.cnh_manager.models.Aula;
import com.project.cnh_manager.repositories.AulaRepository;



@Service
public class AulaService {

    @Autowired
    private AulaRepository aulasRepository;

    public Aula findByCod (Long codAula) {

        Optional<Aula> aulas = this.aulasRepository.findById(codAula);
        return aulas.orElseThrow(() -> new RuntimeException(
            "Aula não encontrada! Codigo da Aula: " + codAula + ", Tipo: " + Aula.class.getName()));
    }

    @Transactional // se qualquer exceção for lançada durante a execução do método, a transação será revertida e todas as operações executadas dentro dela serão desfeitas, mantendo o banco de dados em um estado consistente.
    public Aula create(Aula obj) {
        obj = this.aulasRepository.save(obj);
        return obj;
    }

    @Transactional
    public Aula update(Aula obj) {
        Aula novoObjeto = findByCod(obj.getId());

        //possibilita a atualizacao da carga horaria e do nome da aula
        novoObjeto.setCargaHoraria(obj.getCargaHoraria()); 
        novoObjeto.setNome(obj.getNome());

        return this.aulasRepository.save(novoObjeto);
    }

    public void delete (Long codAula) {
        findByCod(codAula);
        try {
            this.aulasRepository.deleteById(codAula);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir aula pois há entidades relacionadas!");
        }
    }

    public Aula findById(Long id){
        Optional<Aula> obj = this.aulasRepository.findById(id);
        return obj.orElse(null);
    }
}
