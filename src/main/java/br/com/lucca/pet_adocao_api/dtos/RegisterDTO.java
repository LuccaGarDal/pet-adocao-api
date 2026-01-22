package br.com.lucca.pet_adocao_api.dtos;

import br.com.lucca.pet_adocao_api.model.usuario.CargoUsuario;

public record RegisterDTO(String email, String senha, CargoUsuario cargoUsuario) {
}
