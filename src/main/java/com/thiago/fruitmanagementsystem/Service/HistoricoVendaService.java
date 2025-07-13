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
        return null;
    }


//    protected HistoricoVendas createHistoricoVendas(VendaRequestDTO dto) {
//        HistoricoVendas historico = new HistoricoVendas();
//        historico.setDataVenda(LocalDateTime.now());
//        for (FrutasVendasDTO dto2 : dto.frutasVendasDTO()) {
//            historico.setQtdEscolhida(dto2.qtdEscolhida());
//        }
//        return historico;
//    }

    protected void processFruitsSales(VendaRequestDTO dto) {

    }

}
