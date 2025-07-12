package com.thiago.fruitmanagementsystem.Model;

import com.thiago.fruitmanagementsystem.Enums.ClassificacaoEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "frutas")
public class Fruta {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String nome;


    @Enumerated(EnumType.STRING)
    private ClassificacaoEnum classificacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barraca_id")
    private Barraca barraca;

    private Boolean fresca;

    private int qtdDisponivel;

    private double valorVenda;

    private Boolean isOnSale = true;


    public Fruta() {
    }

    public Fruta(String nome, ClassificacaoEnum classificacao, Boolean fresca, int qtdDisponivel, double valorVenda) {
        this.nome = nome;
        this.classificacao = classificacao;
        this.fresca = fresca;
        this.qtdDisponivel = qtdDisponivel;
        this.valorVenda = valorVenda;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ClassificacaoEnum getClassificacao() {
        return classificacao;
    }

    public Boolean getFresca() {
        return fresca;
    }

    public void setClassificacao(ClassificacaoEnum classificacao) {
        this.classificacao = classificacao;
    }

    public Barraca getBarraca() {
        return barraca;
    }

    public void setBarraca(Barraca barraca) {
        this.barraca = barraca;
    }

    public void setFresca(Boolean fresca) {
        this.fresca = fresca;
    }


    public int getQtdDisponivel() {
        return qtdDisponivel;
    }

    public void setQtdDisponivel(int qtdDisponivel) {
        this.qtdDisponivel = qtdDisponivel;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }

    @Override
    public String toString() {
        return "Fruta{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", classificacao=" + classificacao +
                ", fresca=" + fresca +
                ", qtdDisponivel=" + qtdDisponivel +
                ", valorVenda=" + valorVenda +
                '}';
    }

    public Boolean getOnSale() {
        return isOnSale;
    }

    public void setOnSale(Boolean onSale) {
        isOnSale = onSale;
    }
}
