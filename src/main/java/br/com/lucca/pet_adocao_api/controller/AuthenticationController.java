package br.com.lucca.pet_adocao_api.controller;

import br.com.lucca.pet_adocao_api.dtos.AuthenticationDTO;
import br.com.lucca.pet_adocao_api.dtos.LoginResponseDTO;
import br.com.lucca.pet_adocao_api.dtos.RegisterDTO;
import br.com.lucca.pet_adocao_api.dtos.RegisterResponseDTO;
import br.com.lucca.pet_adocao_api.infra.security.TokenService;
import br.com.lucca.pet_adocao_api.model.usuario.UsuarioModel;
import br.com.lucca.pet_adocao_api.repository.UsuarioRepository;
import br.com.lucca.pet_adocao_api.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    TokenService tokenService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login (@RequestBody @Valid AuthenticationDTO dto) {
        var token = authenticationService.login(dto);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping ("/register")
    public ResponseEntity<RegisterResponseDTO> register (@RequestBody @Valid RegisterDTO dto) {
        UsuarioModel user = authenticationService.register(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponseDTO(user.getNome(), user.getEmail()));
    }
}
