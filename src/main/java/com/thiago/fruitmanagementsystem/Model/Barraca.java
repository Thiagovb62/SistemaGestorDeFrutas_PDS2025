package com.thiago.fruitmanagementsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "barraca")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Barraca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String nome;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(
            name = "barraca_frutas",
            joinColumns = @JoinColumn(name = "barraca_id"),
            inverseJoinColumns = @JoinColumn(name = "fruta_id")
    )
    private List<Fruta> frutas;

    @OneToOne
    @JoinColumn(name = "historico_vendas_id")
    private  HistoricoVendas historicoVendas;


    @Column
    private Boolean isAtiva;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", nullable = false)
    private Endereco endereco;

    public HistoricoVendas getHistoricoVendas() {
        return historicoVendas;
    }

    public void setHistoricoVendas(HistoricoVendas historicoVendas) {
        this.historicoVendas = historicoVendas;
    }

    public List<Fruta> getFrutas() {
        return frutas;
    }

    public void setFrutas(List<Fruta> frutas) {
        this.frutas = frutas;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAtiva() {
        return isAtiva;
    }

    public void setAtiva(Boolean ativa) {
        isAtiva = ativa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
