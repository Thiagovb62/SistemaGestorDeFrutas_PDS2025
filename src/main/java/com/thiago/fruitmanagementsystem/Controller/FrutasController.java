package com.thiago.fruitmanagementsystem.Controller;

import com.thiago.fruitmanagementsystem.Model.FrutaResumoDTO;
import com.thiago.fruitmanagementsystem.Model.FrutaRequestDTO;
import com.thiago.fruitmanagementsystem.Service.FrutaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/frutas")
@EnableMethodSecurity(securedEnabled = true)
@Tag(name = "Frutas", description = "Rotas Para Frutas")
public class FrutasController {

    private final FrutaService frutaService;

    public FrutasController(FrutaService frutaService) {
        this.frutaService = frutaService;
    }

    @GetMapping(value = "/findByName", produces = "application/json")
    @Operation(summary = "Busca frutas pelo nome", description = "Busca frutas pelo nome",
            tags = {"Frutas"},
            operationId = "findByName",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro na requisição"),
                    @ApiResponse(responseCode = "401", description = "Sem autorização"),
                    @ApiResponse (responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "404", description = "Fruta não encontrada")

            })
    @Secured("VENDEDOR")
    public ResponseEntity<List<FrutaResumoDTO>> findFruitByName(@RequestParam String nome) {
        return ResponseEntity.ok(frutaService.findFruitByName(nome));
    }

    @GetMapping(value = "/getAll", produces = "application/json")
    @Operation(summary = "Busca todas as frutas", description = "Busca todas as frutas",
            tags = {"Frutas"},
            operationId = "getAll",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro na requisição"),
                    @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "404", description = "Fruta não encontrada")

            })
    @Secured({"VENDEDOR", "ADMIN"})
    public ResponseEntity<List<FrutaResumoDTO>>  getAllFruits() {
        return ResponseEntity.ok(frutaService.getAllFruits());
    }

    @GetMapping(value = "/getByClassification",produces = "application/json")
    @Operation(summary = "Busca frutas pela classificação", description = "Busca frutas pela classificação",
            tags = {"Frutas"},
            operationId = "getByClassification",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro na requisição"),
                    @ApiResponse(responseCode = "401", description = "Sem autorização"),
                    @ApiResponse (responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "404", description = "Fruta não encontrada")

            })
    @Secured("VENDEDOR")
    public ResponseEntity<List<FrutaResumoDTO>> getFruitsByClassification(@RequestParam int classificacao) {
        return ResponseEntity.ok(frutaService.getFruitsByClassification(classificacao));
    }

    @GetMapping(value = "/getByFreshness",produces = "application/json")
    @Operation(summary = "Busca frutas pela frescura", description = "Busca frutas pela frescura",
            tags = {"Frutas"},
            operationId = "getByFreshness",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro na requisição"),
                    @ApiResponse(responseCode = "401", description = "Sem autorização"),
                    @ApiResponse (responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "404", description = "Fruta não encontrada")

            })
    @Secured("VENDEDOR")
    public ResponseEntity<List<FrutaResumoDTO>> getFruitsByFreshness(@RequestParam Boolean fresca) {
        return ResponseEntity.ok(frutaService.getFruitsByFreshness(fresca));
    }


    @GetMapping(value = "/getBySaleValueAsc", produces = "application/json")
    @Operation(summary = "Busca frutas pelo valor de venda crescente", description = "Busca frutas pelo valor de venda crescente",
            tags = {"Frutas"},
            operationId = "getBySaleValueAsc",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro na requisição"),
                    @ApiResponse(responseCode = "401", description = "Sem autorização"),
                    @ApiResponse (responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "404", description = "Fruta não encontrada")

            })
    @Secured("VENDEDOR")
    public ResponseEntity<List<FrutaResumoDTO>>  getFruitsBySaleValueAsc(){
        return ResponseEntity.ok(frutaService.getFruitsBySaleValueAsc());
    }

    @GetMapping("/getBySaleValueDesc")
    @Operation(summary = "Busca frutas pelo valor de venda decrescente", description = "Busca frutas pelo valor de venda decrescente",
            tags = {"Frutas"},
            operationId = "getBySaleValueDesc",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro na requisição"),
                    @ApiResponse(responseCode = "401", description = "Sem autorização"),
                    @ApiResponse(responseCode = "404", description = "Fruta não encontrada")

            })
    @Secured("VENDEDOR")
    public ResponseEntity<List<FrutaResumoDTO>>  getFruitsBySaleValueDesc(){
        return ResponseEntity.ok(frutaService.getFruitsBySaleValueDesc());
    }

    @GetMapping(value = "/getByParams", produces = "application/json")
    @Operation(summary = "Busca frutas por parâmetros", description = "Busca frutas por parâmetros",
            tags = {"Frutas"},
            operationId = "getByParams",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro na requisição"),
                    @ApiResponse(responseCode = "401", description = "Sem autorização"),
                    @ApiResponse(responseCode = "404", description = "Fruta não encontrada")

            })
    @Secured("VENDEDOR")
    public ResponseEntity<List<FrutaResumoDTO>>  getFruitsByParams(
            @Valid @RequestParam(required = false) Integer classificacao,
            @Valid @RequestParam(required = false) Boolean fresca){
        return ResponseEntity.ok(frutaService.findAllByClassificacaoOrFrescaAndOrderByValorVendaIdAsc(classificacao, fresca));
    }

    @PostMapping(value = "/save",consumes = "application/json")
    @Operation(summary = "Salva fruta", description = "Salva fruta",
            tags = {"Frutas"},
            operationId = "save",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Fruta salva com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro na requisição"),
                    @ApiResponse(responseCode = "401", description = "Sem autorização"),
                    @ApiResponse(responseCode = "401", description = "Sem autorização"),
                    @ApiResponse (responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "404", description = "Fruta não encontrada")

            })
    @Secured("ADMIN")
    public ResponseEntity<String> saveFruit(@Valid @RequestBody   FrutaRequestDTO dto){
        return ResponseEntity.ok(frutaService.saveFruit(dto));
    }


    @PatchMapping("/update/{id}")
    @Operation(summary = "Atualiza fruta", description = "Atualiza fruta",
            tags = {"Frutas"},
            operationId = "update",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Fruta atualizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro na requisição"),
                    @ApiResponse(responseCode = "401", description = "Sem autorização"),
                    @ApiResponse (responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "404", description = "Fruta não encontrada")

            })
    @Secured("ADMIN")
    public ResponseEntity< String > updateFruta(@PathVariable Long id, @RequestBody FrutaRequestDTO dto) {
        return frutaService.updateFruta (id, dto);
    }
}
