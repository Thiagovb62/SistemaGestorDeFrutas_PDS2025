package com.thiago.fruitmanagementsystem.PdfUtils;

import com.thiago.fruitmanagementsystem.Model.HistoricoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ExportContext {
    private final Map<String, ExportStrategy> strategies;

    @Autowired
    public ExportContext(Map<String, ExportStrategy> strategies) {
        this.strategies = strategies;
    }

    public byte[] execute(String format, List<HistoricoResponseDTO> data) throws Exception {
        ExportStrategy strategy = strategies.get(format);
        if (strategy == null) {
            throw new IllegalArgumentException("Formato inválido: " + format);
        }
        return strategy.export(data);
    }
}