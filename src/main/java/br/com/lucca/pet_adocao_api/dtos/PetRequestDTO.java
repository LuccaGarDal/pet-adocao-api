package br.com.lucca.pet_adocao_api.dtos;

import br.com.lucca.pet_adocao_api.model.SexoPet;
import br.com.lucca.pet_adocao_api.model.TipoPet;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record PetRequestDTO(
        @NotBlank
        String nomeCompleto,

        @NotNull
        TipoPet tipoPet,

        @NotNull
        SexoPet sexoPet,

        @Min(value = 0, message = "Idade não pode ser negativa")
        @Max(value = 50, message = "Idade inválida")
        Integer idade,

        @Positive (message = "O peso deve ser positivo")
        Double peso,

        @NotBlank(message = "Raça é obrigatória")
        String raca,

        @NotNull(message = "O endereço é obrigatório")
        @Valid
        EnderecoDTO endereco

) {
}
