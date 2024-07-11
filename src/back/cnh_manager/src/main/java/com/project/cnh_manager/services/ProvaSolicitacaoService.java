package com.project.cnh_manager.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.cnh_manager.models.Aula;
import com.project.cnh_manager.models.CargaHorariaConcluida;
import com.project.cnh_manager.models.Pagamento;
import com.project.cnh_manager.models.Prova;
import com.project.cnh_manager.models.ProvaSolicitacao;
import com.project.cnh_manager.models.TipoPagamento;
import com.project.cnh_manager.models.TipoProva;
import com.project.cnh_manager.models.User;
import com.project.cnh_manager.repositories.AulaRepository;
import com.project.cnh_manager.repositories.CargaHorariaConcluidaRepository;
import com.project.cnh_manager.repositories.PagamentoRepository;
import com.project.cnh_manager.repositories.ProvaRepository;
import com.project.cnh_manager.repositories.ProvaSolicitacaoRepository;
import com.project.cnh_manager.repositories.TipoPagamentoRepository;
import com.project.cnh_manager.repositories.TipoProvaRepository;
import com.project.cnh_manager.services.exceptions.AuthorizationException;



@Service
public class ProvaSolicitacaoService {
    @Autowired
    private ProvaSolicitacaoRepository solicitacaoDeProvaRepository;

    @Autowired
    private TipoProvaRepository tipoProvaRepository;

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private CargaHorariaConcluidaRepository chConcluidaRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private TipoPagamentoRepository tipoPagamentoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProvaRepository provaRepository;

    // verifico se o usuário pode solicitar uma prova
    public ProvaSolicitacao verificaAptidaoParaSolicitarProva(ProvaSolicitacao solicitacaoDeProva){
        User user = UserService.authenticated();
        UUID userId = user.getId(); 

        ProvaSolicitacao solicitacao = solicitacaoDeProvaRepository.findByUserId(userId);
        System.out.println(solicitacao);
        
        if (solicitacao != null) {
            throw new RuntimeException("Você não tem permissão para solicitar provas");
        }
        
        ProvaSolicitacao solicitacaoDeProvaCriada = create(solicitacaoDeProva);
        return solicitacaoDeProvaCriada;
    }

    public ProvaSolicitacao create(ProvaSolicitacao solicitacaoDeProva) {
        User user = UserService.authenticated();
        if (Objects.isNull(user))
            throw new AuthorizationException("Acesso negado!");

        TipoProva tipoProva = tipoProvaRepository.findById(Long.valueOf(solicitacaoDeProva.getIdTipoProva()))
                                .orElseThrow(() -> new RuntimeException("Aula não encontrada com o ID fornecido"));

        User usuario = userService.findByLogin(user.getLogin());

        solicitacaoDeProva.setPagamento(null);
        solicitacaoDeProva.setId(null);
        solicitacaoDeProva.setUser(usuario);
        solicitacaoDeProva.setTipoProva(tipoProva);
        solicitacaoDeProva.setStatusAberta(true);
        solicitacaoDeProva.setProva(null);

        String tipoDaProva = tipoProva.getNome();

        if(verificaProvaTeorica(tipoDaProva)){
            if(verificaCargaHorariaTeorica(usuario)){
                solicitacaoDeProva.setStatusProvaElegivel(true);
                TipoPagamento pagamentoProvaPratica = tipoPagamentoRepository.findByNome("Prova Teórica");
                Pagamento pagamento = new Pagamento(pagamentoProvaPratica, user);
                pagamento.setStatusPagamento(false);
                pagamento.setId(null);
                pagamentoRepository.save(pagamento);
                solicitacaoDeProva.setPagamento(pagamento);

                Prova prova = new Prova(usuario, tipoProva);
                prova.setStatusAprovado(false);
                prova.setData(null);
                prova.setHorario(null);
                provaRepository.save(prova);
                solicitacaoDeProva.setProva(prova);
            }
        } else if (!verificaProvaTeorica(tipoDaProva)){
            if(verificarCargaHorariaPratica(usuario)){
                solicitacaoDeProva.setStatusProvaElegivel(true);
                // criar uma folha de pagamento automática aqui que referencia esse aluno em questão
                TipoPagamento pagamentoProvaPratica = tipoPagamentoRepository.findByNome("Prova Prática");
                Pagamento pagamento = new Pagamento(pagamentoProvaPratica, user);
                pagamento.setStatusPagamento(false);
                pagamentoRepository.save(pagamento);
                solicitacaoDeProva.setPagamento(pagamento);

                // cria uma prova vinculada ao usuário elegivel

                Prova prova = new Prova(usuario, tipoProva);
                prova.setStatusAprovado(false);
                prova.setData(null);
                prova.setHorario(null);
                provaRepository.save(prova);

                solicitacaoDeProva.setProva(prova);
            }
        }

        return solicitacaoDeProvaRepository.save(solicitacaoDeProva);
    }

    public ProvaSolicitacao atualizaSolicitacao(UUID id, ProvaSolicitacao solicitacaoDeProva){
        ProvaSolicitacao solicitacao = solicitacaoDeProvaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitação de prova não encontrada com o ID: " + id));

        Pagamento pagamento = solicitacao.getPagamento();
        System.out.println(pagamento);
        solicitacao.setStatusAprovada(solicitacaoDeProva.isStatusAprovada());

        // verificar se o statusAprovada foi pra false ou pra true. Se foi pra true: atualizar status pagamento. Se foi pra false: apagar solicitação.

        if (solicitacao.isStatusAprovada()){
            pagamento.setStatusPagamento(true);
            pagamentoRepository.save(pagamento);
            return solicitacaoDeProvaRepository.save(solicitacao);
        } else {
            deletarSolicitacao(solicitacao);
            solicitacaoDeProvaRepository.delete(solicitacao);
            pagamentoRepository.delete(pagamento);
            return null;
        }

    }

    public void deletarSolicitacao(ProvaSolicitacao solicitacao){
        solicitacaoDeProvaRepository.delete(solicitacao);
    }

    public List<ProvaSolicitacao> findAllByUserId(UUID id){
        return solicitacaoDeProvaRepository.findAll().stream()
                .filter(solicitacao -> solicitacao.getUser().getId().equals(id))
                .toList();
    }

    public void delete(UUID provaSolicitacaoId){
        Optional<ProvaSolicitacao> provaSolicitacao = solicitacaoDeProvaRepository.findById(provaSolicitacaoId);

        solicitacaoDeProvaRepository.delete(provaSolicitacao.get());
    }

    public List<ProvaSolicitacao> getAllSolicitacoesDeProva() {
        return solicitacaoDeProvaRepository.findAll().stream().toList();
    }

    public boolean verificaProvaTeorica(String tipoProva){
        return tipoProva.equals("Prova Teórica");
    }

    public boolean verificarCargaHorariaPratica(User user){
        List<CargaHorariaConcluida> cargasHorariasDoUsuario = chConcluidaRepository.findByUser(user);
        double cargaHorariaTotal=0;

        for(CargaHorariaConcluida ch : cargasHorariasDoUsuario){
            if (ch.getAula().getNome().equals("Prática")){
                double cargaHorariaDoUsuarioTotal = ch.getCargaHoraria();
                System.out.println(cargaHorariaDoUsuarioTotal);
                cargaHorariaTotal = cargaHorariaDoUsuarioTotal;
            }
        }

    
        if (cargaHorariaTotal >= somaCargaHorariaAulasPratica()){
            return true;
        } else if(cargaHorariaTotal < somaCargaHorariaAulasPratica()){
            return false;
        }

        return false;
    }

    public boolean verificaCargaHorariaTeorica(User user){
        // somar a carga horaria concluida das aulas teóricas do aluno e compara com a soma das cargas horarias que existem pra cada aula, sabendo assim que se for igual ou maior, o aluno viu todas as aulas necessárias para realizar a prova

        List<CargaHorariaConcluida> cargasHorariasDoUsuario = chConcluidaRepository.findByUser(user);
        double cargaHorariaDoUsuarioTotal = 0;

        for(CargaHorariaConcluida ch : cargasHorariasDoUsuario){
            cargaHorariaDoUsuarioTotal =+ ch.getCargaHoraria(); 
        }

        if (cargaHorariaDoUsuarioTotal >= somaCargaHorariaAulasTeoricas()){
            return true;
        } else if(cargaHorariaDoUsuarioTotal < somaCargaHorariaAulasTeoricas()){
            return false;
        }

        return false;
    }

    public double somaCargaHorariaAulasTeoricas(){
        List<Aula> aulas = aulaRepository.findAll();
        double cargaHorariaNecessariaTotal = 0;

        for(Aula aula : aulas){
            if (aula.getNome().equals("Prática")){
                continue;
            }
            cargaHorariaNecessariaTotal =+ aula.getCargaHoraria();
        }

        return cargaHorariaNecessariaTotal;
    }

    public double somaCargaHorariaAulasPratica(){
        List<Aula> aulas = aulaRepository.findAll();
        double cargaHorariaNecessariaTotal = 0;

        for(Aula aula : aulas){
            if (aula.getNome().equals("Prática")){
                cargaHorariaNecessariaTotal = aula.getCargaHoraria();
            }
        }

        return cargaHorariaNecessariaTotal;
    }
    
}
