package com.thiago.fruitmanagementsystem.Controller;

import com.itextpdf.text.DocumentException;
import com.thiago.fruitmanagementsystem.Model.HistoricoResponseDTO;
import com.thiago.fruitmanagementsystem.Repository.HistoricoVendaRepository;
import com.thiago.fruitmanagementsystem.Service.HistoricoVendaService;
import com.thiago.fruitmanagementsystem.Utils.PdfUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/historicoVenda")
@EnableMethodSecurity(securedEnabled = true)
@Tag(name = "HistoricoVenda", description = "Rotas Para Histórico de Vendas")
public class HistoricoVendaController {

    private final HistoricoVendaService historicoVendaService;
    private final HistoricoVendaRepository historicoVendaRepository;

    public HistoricoVendaController(HistoricoVendaService historicoVendaService, HistoricoVendaRepository historicoVendaRepository) {
        this.historicoVendaService = historicoVendaService;
        this.historicoVendaRepository = historicoVendaRepository;
    }

    @GetMapping(value = "/all",produces = "application/json")
    @Operation(summary = "Busca todos os históricos de vendas", description = "Busca todos os históricos de vendas",
            tags = {"HistoricoVenda"},
            operationId = "all",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro na requisição"),
                    @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
                    @ApiResponse(responseCode = "404", description = "Histórico de vendas não encontrado")

            })
    @Secured({"VENDEDOR", "ADMIN"})
    public ResponseEntity<List<HistoricoResponseDTO>> findAllHistoricos() {
        return  ResponseEntity.ok(historicoVendaService.findAllHistoricos());
    }

    @GetMapping(value = "/all/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @Operation(summary = "Gera um PDF com todos os históricos de vendas", description = "Gera um PDF com todos os históricos de vendas",
            tags = {"HistoricoVenda"},
            operationId = "allPdf",
            responses = {
                    @ApiResponse(responseCode = "200", description = "PDF gerado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro na requisição"),
                    @ApiResponse(responseCode = "404", description = "Histórico de vendas não encontrado")
            })
    @Secured({"VENDEDOR", "ADMIN"})
    public ResponseEntity<byte[]> findAllHistoricosPdf() throws DocumentException, IOException {
        List<HistoricoResponseDTO> historicos = historicoVendaService.findAllHistoricos();
        if (historicos.isEmpty()) {
            System.out.println("No historical sales data found.");
        } else {
            System.out.println("Historical sales data found: " + historicos.size() + " records.");
        }
        byte[] pdfStream = PdfUtils.generatePdfStream(historicos);
        if (pdfStream.length == 0) {
            System.out.println("PDF generation failed or resulted in an empty PDF.");
        } else {
            System.out.println("PDF generated successfully with size: " + pdfStream.length + " bytes.");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=historico_vendas.pdf");
        PdfUtils.savePdfToFile(pdfStream, "historico_vendas.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfStream.clone());
    }

    @DeleteMapping (value = "/delete/{id}")
    @Operation(summary = "Deleta um histórico de vendas", description = "Deleta um histórico de vendas",
            tags = {"HistoricoVenda"},
            operationId = "delete",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Histórico de vendas deletado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro na requisição"),
                    @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
                    @ApiResponse(responseCode = "404", description = "Histórico de vendas não encontrado")
            })
    @Secured("ADMIN")
    public ResponseEntity<Void> deleteHistorico(@Parameter @PathVariable UUID id) {
        var historico = historicoVendaRepository.findById(id);
        historicoVendaRepository.delete(historico);
        return ResponseEntity.ok().build();
    }
}
