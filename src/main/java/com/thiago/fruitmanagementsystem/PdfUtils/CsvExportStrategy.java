package com.thiago.fruitmanagementsystem.PdfUtils;

import com.thiago.fruitmanagementsystem.Model.HistoricoResponseDTO;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.stream.LangCollectors.collect;

@Component("csv")
public class CsvExportStrategy implements ExportStrategy {
    @Override
    public byte[] export(List<HistoricoResponseDTO> data) {
        String header = "id;valorTotal;frutasVendidas\n";
        String body = data.stream()
                .map(h -> h.id().toString() + ";" + h.barraca().nomeBarraca() + ";"  +
                        h.frutasVendidas().stream()
                                .map(f -> f.frutaVendida().nome() + "(" + f.frutaVendida().classificacao() + ")" + " - " + f.qtdEscolhida() + " - " + f.dataVenda() + " - " + String.format("valorVenda: %.2f", f.contatotal()))
                                .collect(Collectors.joining(",")))
                .collect(Collectors.joining("\n"));
        return (header + body).getBytes(StandardCharsets.UTF_8);
    }
}