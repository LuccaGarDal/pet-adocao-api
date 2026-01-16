package br.com.lucca.pet_adocao_api.dtos;

import jakarta.validation.constraints.NotBlank;

public record EnderecoDTO(
        @NotBlank
        String rua,

        @NotBlank
        String numero,

        @NotBlank
        String bairro,

        @NotBlank
        String cidade
) {
}
