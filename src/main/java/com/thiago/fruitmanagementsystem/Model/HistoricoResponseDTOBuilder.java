package com.thiago.fruitmanagementsystem.Model;

import java.util.List;
import java.util.UUID;

public class HistoricoResponseDTOBuilder {
    private UUID id;
    private Double valorTotal;
    private List<FrutaVendaResponseDTO> frutasVendidas;

    public static HistoricoResponseDTOBuilder builder() {
        return new HistoricoResponseDTOBuilder();
    }

    public HistoricoResponseDTOBuilder id(UUID id) {
        this.id = id;
        return this;
    }

    public HistoricoResponseDTOBuilder valorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
        return this;
    }

    public HistoricoResponseDTOBuilder frutasVendidas(List<FrutaVendaResponseDTO> frutasVendidas) {
        this.frutasVendidas = frutasVendidas;
        return this;
    }

    public HistoricoResponseDTO build() {
        return new HistoricoResponseDTO(id, valorTotal, frutasVendidas);
    }
}