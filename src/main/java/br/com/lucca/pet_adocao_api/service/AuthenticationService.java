package br.com.lucca.pet_adocao_api.service;

import br.com.lucca.pet_adocao_api.dtos.AuthenticationDTO;
import br.com.lucca.pet_adocao_api.dtos.RegisterDTO;
import br.com.lucca.pet_adocao_api.infra.security.TokenService;
import br.com.lucca.pet_adocao_api.model.usuario.UsuarioModel;
import br.com.lucca.pet_adocao_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    TokenService tokenService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public String login (AuthenticationDTO dto) {
        var userAndPass = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
        var auth = authenticationManager.authenticate(userAndPass);

        UsuarioModel user = (UsuarioModel) auth.getPrincipal();
        return tokenService.generateToken(user);
    }

    public UsuarioModel register (RegisterDTO dto) {
        if (usuarioRepository.findByEmail(dto.email()) != null) { throw new BadCredentialsException("Email em uso");}

        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setNome(dto.nome());
        usuarioModel.setEmail(dto.email());
        usuarioModel.setSenha(passwordEncoder.encode(dto.senha()));
        usuarioModel.setCargo(dto.cargoUsuario());

        return usuarioRepository.save(usuarioModel);
    }
}
