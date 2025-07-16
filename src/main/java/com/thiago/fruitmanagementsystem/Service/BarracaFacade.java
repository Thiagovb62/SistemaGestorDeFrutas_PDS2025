package com.thiago.fruitmanagementsystem.Service;

import com.thiago.fruitmanagementsystem.Model.Barraca;
import com.thiago.fruitmanagementsystem.Model.Endereco;
import com.thiago.fruitmanagementsystem.Model.Fruta;
import com.thiago.fruitmanagementsystem.Model.User;
import com.thiago.fruitmanagementsystem.Repository.BarracaRepository;
import com.thiago.fruitmanagementsystem.Repository.EnderecoRepository;
import com.thiago.fruitmanagementsystem.Repository.FrutaRepository;
import com.thiago.fruitmanagementsystem.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BarracaFacade {

    private final BarracaService barracaService;
    private final BarracaRepository barracaRepository;

    public String criarBarracaParaUsuario(Barraca barraca, Long userId) {
        barracaService.adiconarEnderecoBarraca(barraca);
        barracaService.ValidarIfUserHasAbarraca(barraca, userId);
        barracaRepository.save(barraca);
        return "Barraca criada com sucesso para o usuário.";
    }

    public String adicionarFrutasNaBarraca(Long userId, List<Long> frutaIds) {
        barracaService.validarIfBarracaExists (userId, frutaIds);
        barracaService.salvandoFrutasExistentesNaBarraca(userId, frutaIds);
        return "Frutas adicionadas com sucesso à barraca do usuário.";
    }

}