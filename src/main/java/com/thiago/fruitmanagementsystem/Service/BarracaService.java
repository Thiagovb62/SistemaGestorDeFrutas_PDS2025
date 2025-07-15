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


    void ValidarIfUserHasAbarraca(Barraca barraca , Long userId) {
        var userExists = userRepository.findById(userId);
        if (userExists.isPresent() && userExists.get().getBarraca() != null) {
            throw new RuntimeException("Usuário já possui uma barraca associada");
        }
        if (userExists.isPresent()) {
            User user = userExists.get();
            user.setBarraca(barraca);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }

     void validarIfBarracaExists(Long userId, List<Long> frutaIds) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Barraca barraca = user.getBarraca();
            if (barraca == null) {
                throw new RuntimeException("Usuário não possui uma barraca associada");
            }
        }else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }
}