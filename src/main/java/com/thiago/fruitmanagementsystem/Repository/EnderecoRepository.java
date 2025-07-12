package com.thiago.fruitmanagementsystem.Repository;

import com.thiago.fruitmanagementsystem.Model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}
