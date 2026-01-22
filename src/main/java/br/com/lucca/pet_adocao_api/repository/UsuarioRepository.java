package br.com.lucca.pet_adocao_api.repository;

import br.com.lucca.pet_adocao_api.model.usuario.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
    UserDetails findByEmail (String login);

}
