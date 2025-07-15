package com.thiago.fruitmanagementsystem.Service;

import com.thiago.fruitmanagementsystem.Model.*;
import com.thiago.fruitmanagementsystem.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HistoricoVendaService {

    private final HistoricoVendaRepository historicoVendaRepository;
    private final VendaRepository vendaRepository;
    private final UserRepository userRepository;
    private final BarracaRepository barracaRepository;

    public HistoricoVendaService(HistoricoVendaRepository historicoVendaRepository, VendaRepository vendaRepository, UserRepository userRepository, BarracaRepository barracaRepository) {
        this.historicoVendaRepository = historicoVendaRepository;
        this.vendaRepository = vendaRepository;
        this.userRepository = userRepository;
        this.barracaRepository = barracaRepository;
    }


    @Transactional
    public HistoricoResponseDTO findAllHistoricos(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Barraca barraca = barracaRepository.findById(user.getBarraca().getId())
                .orElseThrow(() -> new RuntimeException("Barraca não encontrada para o usuário"));
        List<Venda> historicos = vendaRepository.findAll();
        if (historicos.isEmpty()) {
            throw new RuntimeException("Nenhum histórico de vendas encontrado");
        }
        if (barraca.getHistoricoVendas() == null) {
            HistoricoVendas historicoVendas = new HistoricoVendas();
            historicoVendas.setFrutasVendidas(historicos);
            barraca.setHistoricoVendas(historicoVendas);
            barracaRepository.save(barraca);
            historicoVendaRepository.save(historicoVendas);
            return new HistoricoResponseDTO(
                    historicoVendas.getId(),
                    new BarracaResponseDTO(barraca.getNome(), user.getUsername()),
                    historicos.stream()
                            .map(historico -> new FrutaVendaResponseDTO(
                                    historico.getQtdEscolhida(),
                                    historico.getDataVenda(),
                                    historico.getValorTotal(),
                                    new FrutaResponseDTO(
                                            historico.getFruta().getNome(),
                                            historico.getFruta().getClassificacao(),
                                            historico.getFruta().getFresca(),
                                            historico.getFruta().getValorVenda()
                                    )
                            )).collect(Collectors.toList())
            );
        }else{
            var historicoVendas = barraca.getHistoricoVendas();
            historicoVendas.setFrutasVendidas(historicos);
            historicoVendaRepository.save(historicoVendas);
            return new HistoricoResponseDTO(
                    historicoVendas.getId(),
                    new BarracaResponseDTO(barraca.getNome(), user.getUsername()),
                    historicos.stream()
                            .map(historico -> new FrutaVendaResponseDTO(
                                    historico.getQtdEscolhida(),
                                    historico.getDataVenda(),
                                    historico.getValorTotal(),
                                    new FrutaResponseDTO(
                                            historico.getFruta().getNome(),
                                            historico.getFruta().getClassificacao(),
                                            historico.getFruta().getFresca(),
                                            historico.getFruta().getValorVenda()
                                    )
                            )).collect(Collectors.toList())
            );
        }

    }

    @Transactional
    public String deleteHistorico(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Barraca barraca = barracaRepository.findById(user.getBarraca().getId())
                .orElseThrow(() -> new RuntimeException("Barraca não encontrada para o usuário"));
        HistoricoVendas historicoVendas = Optional.ofNullable(barraca.getHistoricoVendas())
                .orElseThrow(() -> new RuntimeException("Histórico de vendas não encontrado para a barraca"));
        List<Venda> vendas = historicoVendas.getFrutasVendidas();

        barraca.setHistoricoVendas(null);
        barracaRepository.save(barraca);

        historicoVendaRepository.delete(historicoVendas);
        vendaRepository.deleteAllVendasByBarraca_Id(barraca.getId());
        return "Histórico de vendas deletado com sucesso";
    }

}
