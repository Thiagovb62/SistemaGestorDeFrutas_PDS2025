package com.thiago.fruitmanagementsystem.Controller;

import com.thiago.fruitmanagementsystem.Model.VendaRequestDTO;
import com.thiago.fruitmanagementsystem.Service.VendasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venda")
@EnableMethodSecurity(securedEnabled = true)
@Tag(name = "Venda", description = "Rotas Para Vendas")
public class VendaController {

    private final VendasService vendasService;

    public VendaController(VendasService vendasService) {
        this.vendasService = vendasService;
    }

    @PostMapping("/add/{userId}")
    @Transactional
    @Operation(summary = "Adiciona uma nova venda", description = "Adiciona uma nova venda",
            tags = {"Venda"},
            operationId = "add",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Venda adicionada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro na requisição"),
                    @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
                    @ApiResponse(responseCode = "404", description = "Venda não encontrada")

            })
    @Secured("VENDEDOR")
    public ResponseEntity<String> executeSale(@RequestBody @Parameter(name = "dto", description = "DTO para requisição de vendas") VendaRequestDTO dto,
                                             @Parameter(description = "ID do usuário autenticado") @PathVariable Long userId) {
        String response = vendasService.executarVendaComDesControOuSem(dto, userId);
        return ResponseEntity.ok(response);
    }

}
