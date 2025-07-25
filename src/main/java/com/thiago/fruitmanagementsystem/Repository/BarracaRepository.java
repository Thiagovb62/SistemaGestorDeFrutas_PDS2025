package com.thiago.fruitmanagementsystem.Repository;

import com.thiago.fruitmanagementsystem.Model.Barraca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BarracaRepository extends JpaRepository<Barraca, Long> {



    @Query("SELECT b FROM Barraca b WHERE b.id = ?1")
    Optional<Barraca> findBarracaById(Long barracaId);

}
