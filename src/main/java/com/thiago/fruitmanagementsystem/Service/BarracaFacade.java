package com.thiago.fruitmanagementsystem.Service;

import com.thiago.fruitmanagementsystem.Model.*;
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

    public String criarBarracaParaUsuario(Barraca barraca, Long userId) {
        barracaService.adiconarEnderecoBarraca(barraca);
        barracaService.ValidarIfUserHasAbarraca(barraca, userId);
        return "Barraca criada com sucesso para o usuário.";
    }

    public String adicionarFrutasNaBarraca(Long userId, List<Long> frutaIds) {
        barracaService.validarIfBarracaExists (userId, frutaIds);
        barracaService.salvandoFrutasExistentesNaBarraca(userId, frutaIds);
        return "Frutas adicionadas com sucesso à barraca do usuário.";
    }

    public BarracaResponseDTO obterBarracaPorId(Long userId) {
        return barracaService.ListarBarracasAtivas(userId);
    }

    public List<FrutaResumoDTO> ListarFrutasNaBarraca(Long userId) {
        return barracaService.buscarFrutasNaBarracaPorUsuario(userId);
    }

}