package com.thiago.fruitmanagementsystem.Controller;

import com.itextpdf.text.DocumentException;
import com.thiago.fruitmanagementsystem.Model.HistoricoResponseDTO;
import com.thiago.fruitmanagementsystem.PdfUtils.ExportContext;
import com.thiago.fruitmanagementsystem.Repository.HistoricoVendaRepository;
import com.thiago.fruitmanagementsystem.Service.HistoricoVendaFacadeImpl;
import com.thiago.fruitmanagementsystem.Service.HistoricoVendaService;
import com.thiago.fruitmanagementsystem.Utils.PdfUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/historicoVenda")
@EnableMethodSecurity(securedEnabled = true)
@Tag(name = "HistoricoVenda", description = "Rotas Para Histórico de Vendas")
public class HistoricoVendaController {

    private final HistoricoVendaFacadeImpl HistoricoVendaFacadeImpl;
    private final ExportContext exportContext;

    public HistoricoVendaController(HistoricoVendaFacadeImpl HistoricoVendaFacadeImpl, ExportContext exportContext) {
        this.HistoricoVendaFacadeImpl = HistoricoVendaFacadeImpl;

        this.exportContext = exportContext;
    }

    @GetMapping(value = "/all/{userId}",produces = "application/json")
    @Operation(summary = "Busca todos os históricos de vendas do vendedor", description = "Busca todos os históricos de vendas de uma barraca",
            tags = {"HistoricoVenda"},
            operationId = "all",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro na requisição"),
                    @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
                    @ApiResponse(responseCode = "404", description = "Histórico de vendas não encontrado")

            })
    @Secured({"VENDEDOR", "ADMIN"})
    public ResponseEntity<HistoricoResponseDTO> findAllHistoricos(@PathVariable Long userId) {
        return  ResponseEntity.ok(HistoricoVendaFacadeImpl.obterHistoricoVendas(userId));
    }

    @GetMapping(value = "/export/{userId}/{format}", produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(summary = "Gera um PDF com todos os históricos de vendas", description = "Gera um PDF com todos os históricos de vendas",
            tags = {"HistoricoVenda"},
            operationId = "allPdf",
            responses = {
                    @ApiResponse(responseCode = "200", description = "PDF gerado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro na requisição"),
                    @ApiResponse(responseCode = "404", description = "Histórico de vendas não encontrado")
            })
    @Secured({"VENDEDOR", "ADMIN"})
    public ResponseEntity<byte[]> export(
            @PathVariable String format,@Parameter(ref = "ID do vendedor") @PathVariable Long userId ) throws Exception {
        HistoricoResponseDTO dados = HistoricoVendaFacadeImpl.obterHistoricoVendas(userId);
        byte[] output = exportContext.execute(format, Collections.singletonList(dados));
        MediaType mediaType =
                format.equals("pdf") ? MediaType.APPLICATION_PDF :
                        format.equals("csv") ? MediaType.valueOf("text/csv") :
                                MediaType.APPLICATION_JSON;

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=historico." + format)
                .contentType(mediaType)
                .body(output);
    }

    @DeleteMapping(value = "/delete/{userId}")
    @Operation(summary = "Deleta o histórico de vendas do dia", description = "Deleta um histórico de vendas pelo ID",
            tags = {"HistoricoVenda"},
            operationId = "delete",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Histórico de vendas deletado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro na requisição"),
                    @ApiResponse(responseCode = "404", description = "Histórico de vendas não encontrado")
            })
    @Secured({"VENDEDOR", "ADMIN"})
    public ResponseEntity<String> deleteHistorico(@PathVariable Long userId) {
        return ResponseEntity.ok(HistoricoVendaFacadeImpl.limparHistoricoVendas(userId));
    }
}
