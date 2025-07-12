package com.thiago.fruitmanagementsystem.Model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "endereco")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 150)
    private String rua;

    @Column(length = 100)
    private String bairro;

    @Column(length = 100, nullable = false)
    private String cidade;

    @Column(length = 2, nullable = false)
    private String estado;

    @Column(length = 9)
    private String cep;
}