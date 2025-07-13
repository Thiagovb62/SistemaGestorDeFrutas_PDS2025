package com.thiago.fruitmanagementsystem.Model;

import java.time.LocalDateTime;


public record FrutaVendaResponseDTO(
        int qtdEscolhida,
        LocalDateTime dataVenda,
        double contatotal,
        FrutaResponseDTO frutaVendida
) {
}
