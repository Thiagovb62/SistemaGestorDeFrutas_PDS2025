package com.thiago.fruitmanagementsystem.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.thiago.fruitmanagementsystem.Enums.RoleEnum;
import com.thiago.fruitmanagementsystem.Model.UserDTO;
import com.thiago.fruitmanagementsystem.Repository.UserRepository;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.thiago.fruitmanagementsystem.Model.User;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static java.util.Arrays.stream;


@Service
public class UserService {

    @Value("${security.token.secret}")
    String secretKey;

    private final PasswordEncoder encoder;

    private final UserRepository userRepository;

    public UserService(PasswordEncoder encoder, UserRepository userRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    public User buscarUsuario(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public ResponseEntity<List<userResponseDto>> listAllUsers() {
        var users = userRepository.findAll();
        List < userResponseDto > usersList = List.of ( );
        if (!users.isEmpty()) {
            usersList = users.stream ( ).map (
                    user -> new userResponseDto (user.getId ( ) , user.getUsername ( ))
            ).toList ( );
        }

        return ResponseEntity.ok(usersList);

    }

    public void CreateUser(UserDTO dto) throws AuthenticationException {
        if (dto.email().isEmpty() || dto.password().isEmpty()) {
            throw new IllegalArgumentException("Username and password are required");
        }

        var user = userRepository.findByEmailEqualsIgnoreCase(dto.email());

        if (user != null) {
            throw new AuthenticationException("User already exists");
        }

        var password = encoder.encode(dto.password());

        var newUser = new User(dto.email(), password);
        if (dto.email().equalsIgnoreCase("thiagovbAdm@gmail.com"))
         newUser.setRole(RoleEnum.ADMIN);
        else
         newUser.setRole(RoleEnum.VENDEDOR);


        userRepository.save(newUser);
    }

    public String updateUser(Long id , UserDTO dto) {
        var user = userRepository.findByEmailEqualsIgnoreCase(dto.email());
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (!dto.password().isBlank ()) {
            user.setPassword(encoder.encode(dto.password()));
        }

        userRepository.save(user);
        return "User updated successfully";
    }

    public void deleteUser(Long id) {
        var user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        userRepository.deleteById(id);
    }



    public String authenticate(UserDTO dto) throws AuthenticationException {
        if (dto.email().isEmpty() || dto.password().isEmpty()) {
            throw new IllegalArgumentException("Username and password are required");
        }

        var user = userRepository.findByEmailEqualsIgnoreCase(dto.email());

        if (user == null) {
            throw new AuthenticationException("Invalid username or password");
        }

        var passwordMatches = encoder.matches(dto.password(), user.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException("Invalid username or password");
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withSubject(dto.email())
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .withClaim("authorities", user.getAuthorities().toString())
                .sign(algorithm);

    }



    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secretKey);
            return JWT.require(algoritmo)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    public record userUpdateDto(
            String email,
            String password
    ){

    }

    public record userResponseDto(
            Long id,
            String email
    ){

    }

}
