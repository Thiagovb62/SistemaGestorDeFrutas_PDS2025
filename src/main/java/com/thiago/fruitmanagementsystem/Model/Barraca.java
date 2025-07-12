package com.thiago.fruitmanagementsystem.Model;

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
public class Barraca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String nome;


    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(
            name = "barraca_frutas",
            joinColumns = @JoinColumn(name = "barraca_id"),
            inverseJoinColumns = @JoinColumn(name = "fruta_id")
    )
    private BarracaFrutas barracaFrutas;

    @Column
    private Boolean isAtiva;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", nullable = false)
    private Endereco endereco;

    public BarracaFrutas getBarracaFrutas() {
        return barracaFrutas;
    }

    public void setBarracaFrutas(BarracaFrutas barracaFrutas) {
        this.barracaFrutas = barracaFrutas;
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
