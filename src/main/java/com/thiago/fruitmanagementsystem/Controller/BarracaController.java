package com.thiago.fruitmanagementsystem.Controller;

import com.thiago.fruitmanagementsystem.Model.Barraca;
import com.thiago.fruitmanagementsystem.Model.BarracaResponseDTO;
import com.thiago.fruitmanagementsystem.Model.FrutaResumoDTO;
import com.thiago.fruitmanagementsystem.Service.BarracaFacade;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/barracas")
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class BarracaController {

    private final BarracaService barracaService;

    private final BarracaFacade barracaFacade;


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
    public ResponseEntity<String> criarBarraca(@RequestBody Barraca barraca,@PathVariable Long userId) {
        var novaBarraca = barracaFacade.criarBarracaParaUsuario(barraca, userId);
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
    @Secured({"ADMIN"})
    public ResponseEntity<String> adicionarFrutasNaBarraca(
            @Parameter(description = "ID do usuario") @PathVariable Long userId,
            @Parameter(description = "IDs das frutas a serem adicionadas") @RequestBody List<Long> frutaIds) {
        return ResponseEntity.ok(barracaFacade.adicionarFrutasNaBarraca(userId, frutaIds));
    }

    @GetMapping("/obterBarraca/{userId}")
    @Operation(summary = "Obtém barraca por ID do usuário", description = "Obtém a barraca associada ao usuário com o ID fornecido",
            tags = {"Barraca"},
            operationId = "obterBarracaPorId",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Barraca obtida com sucesso"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Barraca não encontrada"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Usuário não autenticado")
            })
    @Secured({"VENDEDOR"})
    public ResponseEntity<BarracaResponseDTO> obterBarracaPorId(@PathVariable Long userId) {
        return ResponseEntity.ok(barracaFacade.obterBarracaPorId(userId));
    }

    @GetMapping("/listarFrutasNaBarraca/{userId}")
    @Operation(summary = "Lista frutas na barraca do usuário", description = "Lista todas as frutas na barraca associada ao usuário com o ID fornecido",
                tags = {"Barraca"},
                operationId = "listarFrutasNaBarraca",
                responses = {
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Frutas listadas com sucesso"),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Barraca não encontrada"),
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Usuário não autenticado")
                })
    @Secured({"VENDEDOR", "ADMIN"})
    public ResponseEntity<List<FrutaResumoDTO>> listarFrutasNaBarraca(@PathVariable Long userId) {
        return ResponseEntity.ok(barracaFacade.ListarFrutasNaBarraca(userId));
    }



}
