package com.thiago.fruitmanagementsystem.Controller;
import com.thiago.fruitmanagementsystem.Model.UserDTO;
import com.thiago.fruitmanagementsystem.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "Rotas Para Usuários")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/all", produces = "application/json")
    @Operation(summary = "Busca todos os usuários", description = "Busca todos os usuários",
            tags = {"User"},
            operationId = "buscarUsuarios",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro na requisição"),
            })
    public ResponseEntity<List<UserService.userResponseDto>> buscarUsuarios() {
        return userService.listAllUsers();
    }

    @PostMapping(value = "/register", consumes = "application/json")
    @Operation(summary = "Cadastra um novo usuário", description = "Cadastra um novo usuário",
            tags = {"User"},
            operationId = "cadastrarUsuario",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro na requisição"),
            })
    public void cadastrarUsuario(@Valid @RequestBody @Parameter(name = "dto", description = "DTO para cadastro de usuários") UserDTO dto) throws AuthenticationException {
        userService.CreateUser(dto);
    }

    @PostMapping(value = "/login", consumes = "application/json")
    @Operation(summary = "Autentica um usuário", description = "Autentica um usuário",
            tags = {"User"},
            operationId = "autenticarUsuario",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro na requisição"),

                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            })
    public String autenticarUsuario(@Valid @RequestBody @Parameter(name = "dto", description = "DTO para autenticação de usuários") UserDTO dto) throws AuthenticationException {
        return userService.authenticate(dto);
    }

    @PatchMapping("update/{id}")
    @Operation(summary = "Atualiza um usuário", description = "Atualiza um usuário",
            tags = {"User"},
            operationId = "atualizarUsuario",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro na requisição"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            })
    public ResponseEntity<String> atualizarUsuario(@PathVariable @Parameter(name = "id") Long id , @RequestBody @Parameter(name = "dto", description = "DTO para atualização de usuários") UserDTO dto) throws AuthenticationException {

        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "Deleta um usuário", description = "Deleta um usuário",
            tags = {"User"},
            operationId = "deletarUsuario",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro na requisição"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
            })
    public ResponseEntity<String> deletarUsuario(@PathVariable @Parameter(name = "id") Long id) throws AuthenticationException {
        userService.deleteUser(id);
        return ResponseEntity.ok("Usuário deletado com sucesso");
    }

}

