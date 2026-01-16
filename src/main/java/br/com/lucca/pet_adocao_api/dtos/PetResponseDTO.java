package br.com.lucca.pet_adocao_api.dtos;

import br.com.lucca.pet_adocao_api.model.EnderecoModel;
import br.com.lucca.pet_adocao_api.model.SexoPet;
import br.com.lucca.pet_adocao_api.model.TipoPet;

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
