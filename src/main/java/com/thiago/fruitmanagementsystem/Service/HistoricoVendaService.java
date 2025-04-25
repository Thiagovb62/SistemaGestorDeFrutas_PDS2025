package com.thiago.fruitmanagementsystem.Service;

import com.thiago.fruitmanagementsystem.Model.*;
import com.thiago.fruitmanagementsystem.Repository.FrutaRepository;
import com.thiago.fruitmanagementsystem.Repository.HistoricoVendaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class HistoricoVendaService {

    private final HistoricoVendaRepository historicoVendaRepository;
    private final FrutaRepository frutaRepository;

    public HistoricoVendaService(HistoricoVendaRepository historicoVendaRepository, com.thiago.fruitmanagementsystem.Repository.FrutaRepository frutaRepository) {
        this.historicoVendaRepository = historicoVendaRepository;
        this.frutaRepository = frutaRepository;
    }

    public List<HistoricoResponseDTO> findAllHistoricos() {
        List<Venda> historicoVendas = historicoVendaRepository.findAllHstoricos();
        Map<UUID, HistoricoResponseDTO> historicoMap = new HashMap<>();

        for (Venda historicoVenda : historicoVendas) {
            UUID historicoId = historicoVenda.getHistoricoVendas().getId();
            if (!historicoMap.containsKey(historicoId)) {
                historicoMap.put(historicoId, new HistoricoResponseDTO(
                        historicoId,
                        historicoVenda.getHistoricoVendas().getValorTotal(),
                        new ArrayList<>()
                ));
            }
            HistoricoResponseDTO historicoResponseDTO = historicoMap.get(historicoId);
            historicoResponseDTO.frutasVendidas().add(new FrutaVendaResponseDTO(
                    historicoVenda.getHistoricoVendas().getQtdEscolhida(),
                    historicoVenda.getHistoricoVendas().getDataVenda(),
                    historicoVenda.getFruta()
            ));
        }
        return new ArrayList<>(historicoMap.values());
    }




    protected HistoricoVendas createHistoricoVendas(VendaRequestDTO dto) {
        HistoricoVendas historico = new HistoricoVendas();
        historico.setDataVenda(LocalDateTime.now());
        for (FrutasVendasDTO dto2 : dto.frutasVendasDTO()) {
            historico.setQtdEscolhida(dto2.qtdEscolhida());
        }
        return historico;
    }

    protected List<Venda> processFruitsSales(VendaRequestDTO dto, HistoricoVendas historico) {
        List<Venda> historicoVendaFruta = new ArrayList<>();


        double totalVenda = 0.0;

        for (FrutasVendasDTO dto2 : dto.frutasVendasDTO()) {
            Fruta fruta = frutaRepository.findById(Long.valueOf(dto2.frutaID())).orElseThrow(() -> new RuntimeException("Fruta não encontrada"));
            double valorVenda = fruta.getValorVenda() * dto2.qtdEscolhida();

            if (dto2.discount() != 0) {
                valorVenda -= (valorVenda * dto2.discount());
            }

            if (dto2.qtdEscolhida() > fruta.getQtdDisponivel()) {
                throw new RuntimeException("Quantidade de fruta Escolhida Maior Do que Quantidade Disponivel");
            }

           valorVenda = Math.round(valorVenda * 100.0) / 100.0;

            fruta.setQtdDisponivel(fruta.getQtdDisponivel() - dto2.qtdEscolhida());
            frutaRepository.save(fruta);


        Venda venda = new Venda();
        venda.setHistoricoVendas(historico);
        venda.setFruta(fruta);
        historicoVendaFruta.add(venda);


            totalVenda += valorVenda;
        }

        historico.setValorTotal(totalVenda);
        return historicoVendaFruta;
    }
}
