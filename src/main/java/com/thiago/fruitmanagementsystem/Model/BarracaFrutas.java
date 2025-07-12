package com.thiago.fruitmanagementsystem.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "barraca_frutas")
public class BarracaFrutas {
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "fruta_id",unique = true)
    private List<Fruta> fruta;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "barraca_id")
    private Barraca barraca;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Fruta> getFruta() {
        return fruta;
    }

    public void setFruta(List<Fruta> fruta) {
        this.fruta = fruta;
    }

    public Barraca getBarraca() {
        return barraca;
    }

    public void setBarraca(Barraca barraca) {
        this.barraca = barraca;
    }
}
