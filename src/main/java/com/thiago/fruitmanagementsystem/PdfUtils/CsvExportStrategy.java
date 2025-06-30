package com.thiago.fruitmanagementsystem.PdfUtils;

import com.thiago.fruitmanagementsystem.Model.HistoricoResponseDTO;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Component("csv")
public class CsvExportStrategy implements ExportStrategy {
    @Override
    public byte[] export(List<HistoricoResponseDTO> data) {
        String header = "id;valorTotal;frutasVendidas\n";
        String body = data.stream()
                .map(h -> h.id().toString() + ";" + h.valorTotal().floatValue() + ";" +
                        h.frutasVendidas().stream()
                                .map(f -> f.frutaVendida().getNome() + "(" + f.frutaVendida().getQtdDisponivel() + ")")
                                .collect(Collectors.joining(",")))
                .collect(Collectors.joining("\n"));
        return (header + body).getBytes(StandardCharsets.UTF_8);
    }
}