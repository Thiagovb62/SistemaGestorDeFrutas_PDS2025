package com.thiago.fruitmanagementsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public record HistoricoResponseDTO(
        UUID  id,
        BarracaResponseDTO barraca,
        List<FrutaVendaResponseDTO> frutasVendidas
) {
}
