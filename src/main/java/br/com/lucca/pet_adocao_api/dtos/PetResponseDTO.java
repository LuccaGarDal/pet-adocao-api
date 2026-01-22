package br.com.lucca.pet_adocao_api.dtos;

import br.com.lucca.pet_adocao_api.model.pet.SexoPet;
import br.com.lucca.pet_adocao_api.model.pet.TipoPet;

import java.time.LocalDateTime;

public record PetResponseDTO(
        Long id,
        String nomeCompleto,
        TipoPet tipoPet,
        SexoPet sexoPet,
        Integer idade,
        Double peso,
        String raca,
        EnderecoDTO endereco,
        LocalDateTime dataCadastro
) {
}
