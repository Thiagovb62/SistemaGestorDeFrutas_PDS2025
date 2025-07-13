package com.thiago.fruitmanagementsystem.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vendas_frutas")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataVenda;

    private Double valorTotal;

    private int qtdEscolhida;

    @ManyToOne
    @JoinColumn(name = "barraca_id", nullable = false)
    private Barraca barraca;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "historico_vendas_id")
    private HistoricoVendas historicoVendas;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fruta_id")
    private Fruta fruta;

    public Venda(Double valorTotal, int qtdEscolhida, Fruta fruta, LocalDateTime dataVenda, Barraca barraca) {
        this.valorTotal = valorTotal;
        this.qtdEscolhida = qtdEscolhida;
        this.fruta = fruta;
        this.dataVenda = dataVenda;
        this.barraca = barraca;
    }

    public Venda() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public Fruta getFruta() {
        return fruta;
    }

    public void setFruta(Fruta fruta) {
        this.fruta = fruta;
    }

    public Barraca getBarraca() {
        return barraca;
    }

    public void setBarraca(Barraca barraca) {
        this.barraca = barraca;
    }

    public HistoricoVendas getHistoricoVendas() {
        return historicoVendas;
    }

    public void setHistoricoVendas(HistoricoVendas historicoVendas) {
        this.historicoVendas = historicoVendas;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    public int getQtdEscolhida() {
        return qtdEscolhida;
    }

    public void setQtdEscolhida(int qtdEscolhida) {
        this.qtdEscolhida = qtdEscolhida;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}