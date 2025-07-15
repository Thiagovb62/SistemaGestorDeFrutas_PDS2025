package com.thiago.fruitmanagementsystem.Repository;

import com.thiago.fruitmanagementsystem.Model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VendaRepository extends JpaRepository<Venda, Long> {



    public Venda findAllByBarraca_Id(Long barracaId);



    @Modifying
    @Query("DELETE FROM Venda v WHERE v.barraca.id = :barracaId")
    void deleteAllVendasByBarraca_Id(Long barracaId);
}
