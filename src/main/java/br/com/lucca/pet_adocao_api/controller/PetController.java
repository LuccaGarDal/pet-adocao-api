package br.com.lucca.pet_adocao_api.controller;

import br.com.lucca.pet_adocao_api.dtos.EnderecoDTO;
import br.com.lucca.pet_adocao_api.dtos.PetRequestDTO;
import br.com.lucca.pet_adocao_api.dtos.PetResponseDTO;
import br.com.lucca.pet_adocao_api.model.EnderecoModel;
import br.com.lucca.pet_adocao_api.model.PetModel;
import br.com.lucca.pet_adocao_api.repository.PetRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// POST(ok), GET(ok), PUT, DELETE

@RestController
public class PetController {

    @Autowired
    PetRepository petRepository;

    @PostMapping("/pets")
    public ResponseEntity<PetResponseDTO> savePet (@RequestBody @Valid PetRequestDTO petRequestDTO) {

        var petModel = new PetModel();
        BeanUtils.copyProperties(petRequestDTO, petModel);

        var endereco = new EnderecoModel();
        BeanUtils.copyProperties(petRequestDTO.endereco(), endereco);
        petModel.setEndereco(endereco);

        var savedPet = petRepository.save(petModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(savedPet));
    }

    @GetMapping("/pets/{id}")
    public ResponseEntity<PetResponseDTO> getPet (@PathVariable(value = "id") Long id) {

        Optional<PetModel> petModel = petRepository.findById(id);

        if (petModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var petSaved = petModel.get();
        return ResponseEntity.status(HttpStatus.OK).body(toResponse(petSaved));
    }

    @GetMapping("/pets")
    public ResponseEntity<List<PetResponseDTO>> getAllPets () {
        List<PetModel> listPets = petRepository.findAll();

        List<PetResponseDTO> response = new ArrayList<>();
        for (PetModel petModel : listPets) {
            response.add(toResponse(petModel));
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/pets/{id}")
    public ResponseEntity<PetResponseDTO> updatePet (@PathVariable(value = "id") Long id,
                                             @RequestBody @Valid PetRequestDTO petRequestDTO) {
        Optional<PetModel> petOptional = petRepository.findById(id);
        if (petOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        var petModel = petOptional.get();
        petModel.setNomeCompleto(petRequestDTO.nomeCompleto());
        petModel.setTipoPet(petRequestDTO.tipoPet());
        petModel.setSexoPet(petRequestDTO.sexoPet());
        petModel.setIdade(petRequestDTO.idade());
        petModel.setPeso(petRequestDTO.peso());
        petModel.setRaca(petRequestDTO.raca());

        EnderecoModel enderecoModel = petModel.getEndereco();
        BeanUtils.copyProperties(petRequestDTO.endereco(), enderecoModel);
        petModel.setEndereco(enderecoModel);
        var savedPet = petRepository.save(petModel);

        return ResponseEntity.status(HttpStatus.OK).body(toResponse(savedPet));
    }

    @DeleteMapping ("/pets/{id}")
    public ResponseEntity<Void> deletePet (@PathVariable (value = "id") Long id) {
        Optional<PetModel> petModel0 = petRepository.findById(id);

        if (petModel0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        petRepository.delete(petModel0.get());
        return ResponseEntity.noContent().build();
    }

    public PetResponseDTO toResponse (PetModel pet) {
        EnderecoDTO enderecoDTO = null;

        var endereco = pet.getEndereco();

        if (pet.getEndereco() != null) {
            enderecoDTO = new EnderecoDTO(
                    endereco.getRua(),
                    endereco.getNumero(),
                    endereco.getBairro(),
                    endereco.getCidade());
        }

        return new PetResponseDTO (
                pet.getId(),
                pet.getNomeCompleto(),
                pet.getTipoPet(),
                pet.getSexoPet(),
                pet.getIdade(),
                pet.getPeso(),
                pet.getRaca(),
                enderecoDTO,
                pet.getDataCadastro()
                );
    }
}
