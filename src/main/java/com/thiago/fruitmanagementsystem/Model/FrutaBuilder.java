package com.thiago.fruitmanagementsystem.Model;

import com.thiago.fruitmanagementsystem.Enums.ClassificacaoEnum;

public class FrutaBuilder {
    private String nome;
    private int classificacao;
    private Boolean fresca;
    private int qtdDisponivel;
    private double valorVenda;

    public static FrutaBuilder builder() {
        return new FrutaBuilder();
    }

    public FrutaBuilder setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public FrutaBuilder setClassificacao(int classificacao) {
        this.classificacao = classificacao;
        return this;
    }

    public FrutaBuilder setFresca(Boolean fresca) {
        this.fresca = fresca;
        return this;
    }

    public FrutaBuilder setQtdDisponivel(int qtdDisponivel) {
        this.qtdDisponivel = qtdDisponivel;
        return this;
    }

    public FrutaBuilder setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
        return this;
    }

    public Fruta build() {
        return new Fruta(nome,ClassificacaoEnum.fromValor(classificacao), fresca, qtdDisponivel, valorVenda);
    }
    public FrutaBuilder fromFruta(Fruta fruta) {
        this.nome = fruta.getNome();
        this.classificacao = fruta.getClassificacao().getValor();
        this.fresca = fruta.getFresca();
        this.qtdDisponivel = fruta.getQtdDisponivel();
        this.valorVenda = fruta.getValorVenda();
        return this;
    }
}
