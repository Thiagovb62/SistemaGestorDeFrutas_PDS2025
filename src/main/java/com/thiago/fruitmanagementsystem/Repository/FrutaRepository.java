package com.thiago.fruitmanagementsystem.Repository;

import com.thiago.fruitmanagementsystem.Enums.ClassificacaoEnum;
import com.thiago.fruitmanagementsystem.Model.Fruta;
import com.thiago.fruitmanagementsystem.Model.FrutaResumoDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FrutaRepository extends JpaRepository<Fruta, Long> {

    @Query("SELECT new com.thiago.fruitmanagementsystem.Model.FrutaResumoDTO(f.id, f.nome, f.classificacao, f.fresca, f.valorVenda, f.qtdDisponivel) FROM Fruta f WHERE f.nome = :nome")
    List<FrutaResumoDTO> findAllByNomeResumo(String nome);

    @Query("SELECT new com.thiago.fruitmanagementsystem.Model.FrutaResumoDTO(f.id, f.nome, f.classificacao, f.fresca, f.valorVenda, f.qtdDisponivel) FROM Fruta f WHERE f.classificacao = :classificacao")
    List<FrutaResumoDTO> findAllByClassificacaoResumo(ClassificacaoEnum classificacao);

    @Query("SELECT new com.thiago.fruitmanagementsystem.Model.FrutaResumoDTO(f.id, f.nome, f.classificacao, f.fresca, f.valorVenda, f.qtdDisponivel) FROM Fruta f WHERE f.fresca = :fresca")
    List<FrutaResumoDTO> findAllByFrescaResumo(Boolean fresca);

    @Query("SELECT new com.thiago.fruitmanagementsystem.Model.FrutaResumoDTO(f.id, f.nome, f.classificacao, f.fresca, f.valorVenda, f.qtdDisponivel) FROM Fruta f WHERE f.classificacao = :classificacao and f.fresca = :fresca order by f.valorVenda asc")
    List<FrutaResumoDTO> findAllByClassificacaoAndFrescaResumo(ClassificacaoEnum classificacao, Boolean fresca);

    @Query("SELECT new com.thiago.fruitmanagementsystem.Model.FrutaResumoDTO(f.id, f.nome, f.classificacao, f.fresca, f.valorVenda, f.qtdDisponivel) FROM Fruta f order by f.valorVenda asc")
    List<FrutaResumoDTO> findAllByValorVendaAscResumo();

    @Query("SELECT new com.thiago.fruitmanagementsystem.Model.FrutaResumoDTO(f.id, f.nome, f.classificacao, f.fresca, f.valorVenda, f.qtdDisponivel) FROM Fruta f order by f.valorVenda desc")
    List<FrutaResumoDTO> findAllByValorVendaDescResumo();

    @Query("SELECT f FROM Fruta f WHERE f.nome = :nome")
    Optional<Fruta> findByNomeAndClassificacao(@NotBlank(message = "O nome da fruta é obrigatório") String nome,@NotNull ClassificacaoEnum classificacaoEnum);

    @Query("SELECT new com.thiago.fruitmanagementsystem.Model.FrutaResumoDTO(f.id, f.nome, f.classificacao, f.fresca, f.valorVenda, f.qtdDisponivel) FROM Fruta f")
    List<FrutaResumoDTO> findAllFrutas();
}
