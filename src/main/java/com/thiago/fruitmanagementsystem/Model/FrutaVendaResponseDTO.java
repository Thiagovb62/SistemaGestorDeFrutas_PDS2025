package com.thiago.fruitmanagementsystem.Model;

import java.time.LocalDateTime;


public record FrutaVendaResponseDTO(
        int qtdEscolhida,
        LocalDateTime dataVenda,
        Fruta frutaVendida
) {
    public FrutaVendaResponseDTO(LocalDateTime dataVenda, int qtdDisponivel, Fruta frutaVendida) {
        this(qtdDisponivel, dataVenda, frutaVendida);

    }
}
