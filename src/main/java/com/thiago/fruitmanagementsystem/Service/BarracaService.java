package com.thiago.fruitmanagementsystem.Service;

import com.thiago.fruitmanagementsystem.Model.Barraca;
import com.thiago.fruitmanagementsystem.Model.Endereco;
import com.thiago.fruitmanagementsystem.Model.User;
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
public class BarracaService {

    private final BarracaRepository barracaRepository;
    private final EnderecoRepository enderecoRepository;
    private final FrutaRepository frutaRepository;
    private final UserRepository userRepository;

    @Transactional
    public Barraca criarBarraca(Barraca barraca, Long userId) {
        Barraca barracaCreated = barracaRepository.save(barraca);
        Endereco endereco = barracaCreated.getEndereco();
        enderecoRepository.save(endereco);
        Optional<User> userExists = userRepository.findById(userId);
        if (userExists.isPresent()) {
            User user = userExists.get();
            user.setBarraca(barracaCreated);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
        return barraca;
    }

    public Barraca adicionarFrutasNaBarraca(Long barracaId, List<Long> frutaIds) {
        // Lógica para buscar a barraca e adicionar as frutas pelo id
        // Exemplo:
        // Barraca barraca = barracaRepository.findById(barracaId).orElseThrow();
        // List<Fruta> frutas = frutaRepository.findAllById(frutaIds);
        // barraca.getFrutas().addAll(frutas);
        // return barracaRepository.save(barraca);
        return null;
    }
}