package br.com.lucca.pet_adocao_api.mapper;

import br.com.lucca.pet_adocao_api.dtos.EnderecoDTO;
import br.com.lucca.pet_adocao_api.dtos.PetRequestDTO;
import br.com.lucca.pet_adocao_api.dtos.PetResponseDTO;
import br.com.lucca.pet_adocao_api.model.EnderecoModel;
import br.com.lucca.pet_adocao_api.model.PetModel;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;

public class PetMapper {

    private PetMapper () {}

    public static PetModel toModel (PetRequestDTO petRequestDTO) {
        var petModel = new PetModel();
        BeanUtils.copyProperties(petRequestDTO, petModel);

        var endereco = new EnderecoModel();
        BeanUtils.copyProperties(petRequestDTO.endereco(), endereco);
        petModel.setEndereco(endereco);

        return petModel;
    }

    public static PetResponseDTO toResponse (PetModel petModel) {
        var endereco = petModel.getEndereco();

        return new PetResponseDTO(
                petModel.getId(),
                petModel.getNomeCompleto(),
                petModel.getTipoPet(),
                petModel.getSexoPet(),
                petModel.getIdade(),
                petModel.getPeso(),
                petModel.getRaca(),
                new EnderecoDTO(
                        endereco.getRua(),
                        endereco.getNumero(),
                        endereco.getBairro(),
                        endereco.getCidade()
                ),
                petModel.getDataCadastro()
        );
    }

    public static void updatePet (PetModel petModel, PetRequestDTO petRequestDTO) {
        petModel.setNomeCompleto(petRequestDTO.nomeCompleto());
        petModel.setTipoPet(petRequestDTO.tipoPet());
        petModel.setSexoPet(petRequestDTO.sexoPet());
        petModel.setIdade(petRequestDTO.idade());
        petModel.setPeso(petRequestDTO.peso());
        petModel.setRaca(petRequestDTO.raca());

        if (petModel.getEndereco() == null) {
            petModel.setEndereco(new EnderecoModel());
        }

        BeanUtils.copyProperties(petRequestDTO.endereco(), petModel.getEndereco());
    }
}
