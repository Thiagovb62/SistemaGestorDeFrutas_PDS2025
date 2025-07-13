package com.thiago.fruitmanagementsystem.Model;

import com.thiago.fruitmanagementsystem.Enums.ClassificacaoEnum;

public record FrutaResponseDTO(
        String nome,
        ClassificacaoEnum classificacao,
        Boolean fresca,
        double valorVenda

) {
}
