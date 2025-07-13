package com.thiago.fruitmanagementsystem.Repository;

import com.thiago.fruitmanagementsystem.Model.Venda;
import com.thiago.fruitmanagementsystem.Model.HistoricoVendas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface HistoricoVendaRepository extends JpaRepository<HistoricoVendas, Long> {

//    @Query("select distinct hf from HistoricoVendas hv join Venda hf on hv.id = hf.historicoVendas.id join Fruta f on hf.fruta.id = f.id")
//    List<Venda> findAllHstoricos();


    HistoricoVendas findById(UUID id);
}
