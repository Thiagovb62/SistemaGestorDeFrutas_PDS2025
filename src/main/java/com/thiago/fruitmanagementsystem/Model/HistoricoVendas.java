package com.thiago.fruitmanagementsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @OneToMany(mappedBy = "historicoVendas", cascade = CascadeType.PERSIST)
    private List<Venda> frutasVendidas;


    public HistoricoVendas() {
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Venda> getFrutasVendidas() {
        return frutasVendidas;
    }

    public void setFrutasVendidas(List<Venda> frutasVendidas) {
        this.frutasVendidas = frutasVendidas;
    }
}
