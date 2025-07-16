// src/main/java/com/thiago/fruitmanagementsystem/DTO/FrutaResumoDTO.java
package com.thiago.fruitmanagementsystem.Model;

import com.thiago.fruitmanagementsystem.Enums.ClassificacaoEnum;

public record FrutaResumoDTO (
     Long id,
     String nome,
     ClassificacaoEnum classificacao,
     Boolean fresca,
     Double valorVenda,
     Integer qtdDisponivel
){

    public FrutaResumoDTO(Long id, String nome, ClassificacaoEnum classificacao, Boolean fresca, Double valorVenda, Integer qtdDisponivel) {
        this.id = id;
        this.nome = nome;
        this.classificacao = classificacao;
        this.fresca = fresca;
        this.valorVenda = valorVenda;
        this.qtdDisponivel = qtdDisponivel;
    }

    // getters e setters
}