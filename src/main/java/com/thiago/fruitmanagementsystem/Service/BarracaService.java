package com.thiago.fruitmanagementsystem.Service;

import com.thiago.fruitmanagementsystem.Model.*;
import com.thiago.fruitmanagementsystem.Repository.BarracaRepository;
import com.thiago.fruitmanagementsystem.Repository.EnderecoRepository;
import com.thiago.fruitmanagementsystem.Repository.FrutaRepository;
import com.thiago.fruitmanagementsystem.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BarracaService{

    private final BarracaRepository barracaRepository;
    private final EnderecoRepository enderecoRepository;
    private final FrutaRepository frutaRepository;
    private final UserRepository userRepository;

    @Transactional
    public Barraca criarBarracaParaUsuario(Barraca barraca, Long userId) {
        Barraca barracaCreated = barracaRepository.save(barraca);
        Endereco endereco = barracaCreated.getEndereco();
        enderecoRepository.save(endereco);
        Optional<User> userExists = userRepository.findById(userId);
        if (userExists.isPresent() && userExists.get().getBarraca() != null) {
            throw new RuntimeException("Usuário já possui uma barraca associada");
        }
        if (userExists.isPresent()) {
            User user = userExists.get();
            user.setBarraca(barracaCreated);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
        return barraca;
    }

    public String adicionarFrutasNaBarraca(Long userId, List<Long> frutaIds) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Barraca barraca = user.getBarraca();
            if (barraca == null) {
                throw new RuntimeException("Usuário não possui uma barraca associada");
            }
            List<Fruta> frutas = frutaRepository.findAllById(frutaIds);
            frutas.forEach(fruta -> {
                fruta.setBarraca(barraca);
                frutaRepository.save(fruta);
            });
            barraca.getFrutas().addAll(frutas);
            barracaRepository.save(barraca);
        }
        return "Frutas adicionadas com sucesso à barraca do usuário com ID: " + userId;
    }
}