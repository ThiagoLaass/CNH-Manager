package com.project.cnh_manager.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.cnh_manager.models.HorarioAulaPratica;
import com.project.cnh_manager.models.Pagamento;
import com.project.cnh_manager.models.TipoPagamento;
import com.project.cnh_manager.models.User;
import com.project.cnh_manager.repositories.PagamentoRepository;
import com.project.cnh_manager.repositories.TipoPagamentoRepository;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private TipoPagamentoRepository tipoPagamentoRepository;

    public List<Pagamento> getAllPagamento() {
        List<Pagamento> pagamentos = pagamentoRepository.findAll().stream().toList();
        return pagamentos;
    }

    public List<Pagamento> getAllById(UUID id) {
        return pagamentoRepository.findAll().stream()
                .filter(pagamento -> pagamento.getUser().getId().equals(id))
                .toList();
    }

    public Pagamento atualizarPagamento(UUID id, Pagamento pagamentoAtualizado) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrada com o ID: " + id));

        pagamento.setStatusPagamento(pagamentoAtualizado.isStatusPagamento());
        return pagamentoRepository.save(pagamento);
    }

    public Pagamento createPagamentoeTipoPagamento(User user) {
        Pagamento pagamento = new Pagamento();

        TipoPagamento tipoPagamento = tipoPagamentoRepository.findById(0L).orElseThrow(); /** de acordo com o que estiver no BD, 
        se não houver nenhum TipoPagamento cadastrado, a request ira retornar um erro 403, 
        assim que implementado os scripts de automatização, isso será resolvido.
        Caso queira testar a funcionalidade, crie um a mão no bd com um nome condizente
        */ 

        pagamento.setId(UUID.randomUUID());
        pagamento.setUser(user);
        pagamento.setData(LocalDate.now());
        pagamento.setHorario(LocalTime.now());
        pagamento.setTipoPagamento(tipoPagamento);
        pagamento.setStatusPagamento(false);

        return pagamentoRepository.save(pagamento);
    }

    public Pagamento approvePagamento(HorarioAulaPratica horario) {
        Pagamento p = horario.getPagamento();
        p.setStatusPagamento(true);
        return pagamentoRepository.save(p);
    }

    public Pagamento denyPagamento(HorarioAulaPratica horario){
        Pagamento p = horario.getPagamento();
        p.setStatusPagamento(false);
        return pagamentoRepository.save(p);
    }
}
