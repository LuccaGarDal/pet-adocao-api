package br.com.lucca.pet_adocao_api.dtos;

import br.com.lucca.pet_adocao_api.model.usuario.CargoUsuario;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(@NotEmpty(message = "O nome é obrigatório") String nome,
                          @NotEmpty(message = "O e-mail é obrigatório") String email,
                          @NotEmpty(message = "A senha é obrigatória") String senha,
                          @NotNull(message = "O cargo é obrigatório") CargoUsuario cargoUsuario) {
}
