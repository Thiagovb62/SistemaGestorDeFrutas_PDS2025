package com.thiago.fruitmanagementsystem.Model;

import com.thiago.fruitmanagementsystem.Enums.ClassificacaoEnum;

public record FrutaHistoricoResponseDTO(
        String nome,
        ClassificacaoEnum classificacao,
        Boolean fresca,
        double valorVenda

) {
}
