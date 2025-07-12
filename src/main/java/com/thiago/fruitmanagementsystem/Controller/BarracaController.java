package com.thiago.fruitmanagementsystem.Controller;

import com.thiago.fruitmanagementsystem.Model.Barraca;
import com.thiago.fruitmanagementsystem.Service.BarracaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.Transient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/barracas")
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class BarracaController {

    private final BarracaService barracaService;

    @PostMapping("/criar/{userId}")
    @Operation(summary = "Cria uma nova barraca", description = "Cria uma nova barraca com as informações fornecidas",
            tags = {"Barraca"},
            operationId = "criarBarraca",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Barraca criada com sucesso"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Erro na requisição"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Usuário não autenticado")
            })
    @Secured({"VENDEDOR"})
    public ResponseEntity<Barraca> criarBarraca(@RequestBody Barraca barraca,@PathVariable Long userId) {
        Barraca novaBarraca = barracaService.criarBarracaParaUsuario(barraca, userId);
        return ResponseEntity.ok(novaBarraca);
    }

    @PostMapping("/adicionarFrutas/{userId}")
    @Operation(summary = "Adiciona frutas à barraca", description = "Adiciona frutas à barraca com o ID fornecido",
            tags = {"Barraca"},
            operationId = "adicionarFrutasNaBarraca",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Frutas adicionadas com sucesso"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Erro na requisição"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Usuário não autenticado")
            })
    @Secured({"VENDEDOR"})
    public ResponseEntity<String> adicionarFrutasNaBarraca(
            @Parameter(description = "ID do usuario") @PathVariable Long userId,
            @Parameter(description = "IDs das frutas a serem adicionadas") @RequestBody List<Long> frutaIds) {
        return ResponseEntity.ok(barracaService.adicionarFrutasNaBarraca(userId, frutaIds));
    }




}
