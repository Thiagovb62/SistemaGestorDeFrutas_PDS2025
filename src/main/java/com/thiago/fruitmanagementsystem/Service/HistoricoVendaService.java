package com.thiago.fruitmanagementsystem.Service;

import com.thiago.fruitmanagementsystem.Model.*;
import com.thiago.fruitmanagementsystem.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HistoricoVendaService {

    private final HistoricoVendaRepository historicoVendaRepository;
    private final BarracaService barracaService;
    private final UserService userService;
    private final VendasService vendaService;

    public HistoricoVendaService(HistoricoVendaRepository historicoVendaRepository,
                                BarracaService barracaService,
                                UserService userService,
                                VendasService vendaService) {
        this.historicoVendaRepository = historicoVendaRepository;
        this.barracaService = barracaService;
        this.userService = userService;
        this.vendaService = vendaService;
    }

    @Transactional
    public HistoricoResponseDTO findAllHistoricos(long userId) {
        User user = this.userService.buscarUsuario(userId);
        Barraca barraca = barracaService.buscarBarracaPorUsuario(user);
        List<Venda> historicos = vendaService.buscarVendas();

        return processarHistoricoVendas(barraca, historicos, user);
    }

    @Transactional
    public String deleteHistorico(Long userId) {
        User user = this.userService.buscarUsuario(userId);
        Barraca barraca = barracaService.buscarBarracaPorUsuario(user);
        HistoricoVendas historicoVendas = Optional.ofNullable(barraca.getHistoricoVendas())
                .orElseThrow(() -> new RuntimeException("Histórico de vendas não encontrado para a barraca"));

        removerHistoricoDeVendas(barraca, historicoVendas,userId);
        return "Histórico de vendas deletado com sucesso";
    }

    private HistoricoResponseDTO processarHistoricoVendas(Barraca barraca, List<Venda> historicos, User user) {
        if (barraca.getHistoricoVendas() == null) {
            return criarNovoHistorico(barraca, historicos, user);
        } else {
            return atualizarHistoricoExistente(barraca, historicos, user);
        }
    }

    private HistoricoResponseDTO criarNovoHistorico(Barraca barraca, List<Venda> historicos, User user) {
        HistoricoVendas historicoVendas = new HistoricoVendas();
        historicoVendas.setFrutasVendidas(historicos);
        this.barracaService.setarHistoricoNaBarraca(barraca, historicoVendas);
        historicoVendaRepository.save(historicoVendas);

        return montarHistoricoResponse(historicoVendas, barraca, user, historicos);
    }


    private HistoricoResponseDTO atualizarHistoricoExistente(Barraca barraca, List<Venda> historicos, User user) {
        var historicoVendas = barraca.getHistoricoVendas();
        historicoVendas.setFrutasVendidas(historicos);
        historicoVendaRepository.save(historicoVendas);

        return montarHistoricoResponse(historicoVendas, barraca, user, historicos);
    }

    private HistoricoResponseDTO montarHistoricoResponse(HistoricoVendas historicoVendas, Barraca barraca, User user, List<Venda> historicos) {
        return new HistoricoResponseDTO(
                historicoVendas.getId(),
                new BarracaResponseDTO(barraca.getNome(), user.getUsername()),
                historicos.stream()
                        .map(this::mapearParaFrutaVendaResponseDTO)
                        .collect(Collectors.toList())
        );
    }

    private FrutaVendaResponseDTO mapearParaFrutaVendaResponseDTO(Venda historico) {
        return new FrutaVendaResponseDTO(
                historico.getQtdEscolhida(),
                historico.getDataVenda(),
                historico.getValorTotal(),
                new FrutaHistoricoResponseDTO(
                        historico.getFruta().getNome(),
                        historico.getFruta().getClassificacao(),
                        historico.getFruta().getFresca(),
                        historico.getFruta().getValorVenda()
                )
        );
    }

    private void removerHistoricoDeVendas(Barraca barraca, HistoricoVendas historicoVendas,Long userId) {
        this.barracaService.excluirHistoricoAtualDaBarraca(barraca);
        vendaService.deleteAllVendas(userId);
        historicoVendaRepository.delete(historicoVendas);

    }

}