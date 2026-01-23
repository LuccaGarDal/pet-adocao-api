package br.com.lucca.pet_adocao_api.controller;

import br.com.lucca.pet_adocao_api.dtos.AuthenticationDTO;
import br.com.lucca.pet_adocao_api.dtos.LoginResponseDTO;
import br.com.lucca.pet_adocao_api.dtos.RegisterDTO;
import br.com.lucca.pet_adocao_api.infra.security.TokenService;
import br.com.lucca.pet_adocao_api.model.usuario.UsuarioModel;
import br.com.lucca.pet_adocao_api.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Valid AuthenticationDTO dto) {
        var emailSenha = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
        var auth = authenticationManager.authenticate(emailSenha);

        var token = tokenService.generateToken((UsuarioModel) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping ("/register")
    public ResponseEntity register (@RequestBody @Valid RegisterDTO dto) {
        if (this.usuarioRepository.findByEmail(dto.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedSenha = new BCryptPasswordEncoder().encode(dto.senha());
        UsuarioModel user = new UsuarioModel(dto.email(), encryptedSenha, dto.cargoUsuario());

        this.usuarioRepository.save(user);

        return ResponseEntity.ok().build();
    }
}
