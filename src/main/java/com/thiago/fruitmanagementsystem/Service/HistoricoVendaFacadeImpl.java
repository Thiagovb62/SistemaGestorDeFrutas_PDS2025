package com.thiago.fruitmanagementsystem.Service;

import com.thiago.fruitmanagementsystem.Model.HistoricoResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class HistoricoVendaFacadeImpl {

    private final HistoricoVendaService historicoVendaService;

    public HistoricoVendaFacadeImpl(HistoricoVendaService historicoVendaService) {
        this.historicoVendaService = historicoVendaService;
    }
    public HistoricoResponseDTO obterHistoricoVendas(long userId) {
        return historicoVendaService.findAllHistoricos(userId);
    }

    public String limparHistoricoVendas(Long userId) {
        return historicoVendaService.deleteHistorico(userId);
    }
}