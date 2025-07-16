package com.thiago.fruitmanagementsystem.Service;

import com.thiago.fruitmanagementsystem.Model.Barraca;
import com.thiago.fruitmanagementsystem.Model.Endereco;
import com.thiago.fruitmanagementsystem.Repository.EnderecoRepository;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

   Endereco adiconarEnderecoNaTabela(Barraca barraca) {
       return enderecoRepository.save(barraca.getEndereco());
    }
}
