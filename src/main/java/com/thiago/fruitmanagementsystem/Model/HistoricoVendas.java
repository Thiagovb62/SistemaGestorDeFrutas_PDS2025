package com.thiago.fruitmanagementsystem.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "historico_vendas")
public class HistoricoVendas {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    private UUID id;

    private LocalDateTime dataVenda;

    private Double valorTotal;


    private int qtdEscolhida;

    @OneToMany(mappedBy = "historicoVendas", cascade = CascadeType.PERSIST)
    private List<Venda> frutasVendidas;

    public HistoricoVendas(UUID id, LocalDateTime dataVenda, Double valorTotal, List<Venda> frutasVendidas, int qtdEscolhida) {
        this.id = id;
        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal;
        this.frutasVendidas = frutasVendidas;
        this.qtdEscolhida = qtdEscolhida;
    }

    public HistoricoVendas() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getQtdEscolhida() {
        return qtdEscolhida;
    }

    public void setQtdEscolhida(int qtdEscolhida) {
        this.qtdEscolhida = qtdEscolhida;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<Venda> getFrutasVendidas() {
        return frutasVendidas;
    }

    public void setFrutasVendidas(List<Venda> frutasVendidas) {
        this.frutasVendidas = frutasVendidas;
    }
}
