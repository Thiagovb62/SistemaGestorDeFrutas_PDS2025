package com.thiago.fruitmanagementsystem.Service;

import com.thiago.fruitmanagementsystem.Model.*;
import com.thiago.fruitmanagementsystem.Repository.FrutaRepository;
import com.thiago.fruitmanagementsystem.Repository.HistoricoVendaRepository;
import com.thiago.fruitmanagementsystem.Repository.UserRepository;
import com.thiago.fruitmanagementsystem.Repository.VendaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VendasService  {

    private final VendaRepository vendaRepository;
    private final UserRepository userRepository;
    private final FrutaRepository frutaRepository;

    VendasService(VendaRepository vendaRepository, UserRepository userRepository, FrutaRepository frutaRepository) {
        this.vendaRepository = vendaRepository;
        this.userRepository = userRepository;
        this.frutaRepository = frutaRepository;
    }

    public String executarVendaComDesControOuSem(VendaRequestDTO dto,Long UserId) {

        User user = userRepository.findById(UserId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));


        var barraca = user.getBarraca();
        if (barraca == null) {
            throw new RuntimeException("Usuário não possui uma barraca associada");
        }
        dto.frutasVendasDTO().forEach(frutas ->{
            System.out.println(barraca.getFrutas());
            if (!barraca.getFrutas().stream().anyMatch(
                    fruta -> fruta.getBarraca().getId().equals(barraca.getId())))  {
                throw new RuntimeException("Fruta com ID " + frutas.frutaID() + " não está disponível na barraca do usuário");
            }

            Fruta fruta = frutaRepository.findById(frutas.frutaID())
                    .orElseThrow(() -> new RuntimeException("Fruta não encontrada com ID: " + frutas.frutaID()));
            if (fruta.getQtdDisponivel() < frutas.qtdEscolhida()) {
                throw new RuntimeException("Quantidade insuficiente de " + fruta.getNome() + " na barraca do usuário");
            }

            fruta.setQtdDisponivel(fruta.getQtdDisponivel() - frutas.qtdEscolhida());
            frutaRepository.save(fruta);

            if (frutas.discount() > 0){
                double valorTotal = frutas.qtdEscolhida() * fruta.getValorVenda();
                double valorComDesconto = valorTotal - (valorTotal * frutas.discount() / 100);

                Venda venda = new Venda(valorComDesconto, frutas.qtdEscolhida(),fruta, LocalDateTime.now(), barraca);
                vendaRepository.save(venda);
            } else {
                double valorTotal = frutas.qtdEscolhida() * fruta.getValorVenda();

                Venda venda = new Venda(valorTotal, frutas.qtdEscolhida(), fruta, LocalDateTime.now(), barraca);
                vendaRepository.save(venda);
            }
        });

        return "Venda realizada com sucesso!";

    }

    public List<Venda> buscarVendas() {
        List<Venda> historicos = vendaRepository.findAll();
        if (historicos.isEmpty()) {
            throw new RuntimeException("Nenhum histórico de vendas encontrado");
        }
        return historicos;
    }

    public void deleteAllVendas(Long userId) {

        List<Venda> vendas =  this.buscarVendas();

        vendaRepository.deleteAll(vendas);
    }

}
