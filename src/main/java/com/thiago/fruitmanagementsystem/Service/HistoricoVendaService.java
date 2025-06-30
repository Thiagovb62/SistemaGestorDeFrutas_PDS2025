package com.thiago.fruitmanagementsystem.Service;

import com.thiago.fruitmanagementsystem.Model.*;
import com.thiago.fruitmanagementsystem.Repository.FrutaRepository;
import com.thiago.fruitmanagementsystem.Repository.HistoricoVendaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HistoricoVendaService {

    private final HistoricoVendaRepository historicoVendaRepository;
    private final FrutaRepository frutaRepository;

    public HistoricoVendaService(HistoricoVendaRepository historicoVendaRepository, com.thiago.fruitmanagementsystem.Repository.FrutaRepository frutaRepository) {
        this.historicoVendaRepository = historicoVendaRepository;
        this.frutaRepository = frutaRepository;
    }

    public List<HistoricoResponseDTO> findAllHistoricos() {

            return historicoVendaRepository.findAll().stream()
                    .map(entity -> HistoricoResponseDTOBuilder.builder()
                            .id(entity.getId())
                            .valorTotal(entity.getValorTotal())
                            .frutasVendidas(entity.getFrutasVendidas().stream()
                                    .map(fr -> new FrutaVendaResponseDTO(
                                            entity.getQtdEscolhida(),
                                            entity.getDataVenda(),
                                            fr.getFruta()))
                                    .collect(Collectors.toList()))
                            .build()
                    )
                    .collect(Collectors.toList());
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
