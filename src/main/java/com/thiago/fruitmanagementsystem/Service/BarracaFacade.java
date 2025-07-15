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
    private final EnderecoRepository enderecoRepository;
    private final FrutaRepository frutaRepository;
    private final UserRepository userRepository;

    public String criarBarracaParaUsuario(Barraca barraca, Long userId) {
        Barraca barracaCreated = barracaRepository.save(barraca);
        Endereco endereco = barracaCreated.getEndereco();
        enderecoRepository.save(endereco);
        barracaService.ValidarIfUserHasAbarraca(barracaCreated, userId);
        return "Barraca criada com sucesso para o usuário.";
    }

    public String adicionarFrutasNaBarraca(Long userId, List<Long> frutaIds) {
        barracaService.validarIfBarracaExists (userId, frutaIds);
        Optional<User> userOptional = userRepository.findById(userId);
        var barraca = userOptional.get ( ).getBarraca ( );
        List< Fruta > frutas = frutaRepository.findAllById(frutaIds);
        frutas.forEach(fruta -> {
            fruta.setBarraca(barraca);
            frutaRepository.save(fruta);
        });
        barraca.getFrutas().addAll(frutas);
        barracaRepository.save(barraca);
        return "Frutas adicionadas com sucesso à barraca do usuário.";
    }
}