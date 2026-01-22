package br.com.lucca.pet_adocao_api.controller;

import br.com.lucca.pet_adocao_api.dtos.PetRequestDTO;
import br.com.lucca.pet_adocao_api.dtos.PetResponseDTO;
import br.com.lucca.pet_adocao_api.mapper.PetMapper;
import br.com.lucca.pet_adocao_api.model.pet.PetModel;
import br.com.lucca.pet_adocao_api.repository.PetRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

        var petModel = PetMapper.toModel(petRequestDTO);

        var savedPet = petRepository.save(petModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(PetMapper.toResponse(savedPet));
    }

    @GetMapping("/pets/{id}")
    public ResponseEntity<PetResponseDTO> getPet (@PathVariable(value = "id") Long id) {

        Optional<PetModel> petModel = petRepository.findById(id);

        if (petModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var petSaved = petModel.get();
        return ResponseEntity.status(HttpStatus.OK).body(PetMapper.toResponse(petSaved));
    }

    @GetMapping("/pets")
    public ResponseEntity<List<PetResponseDTO>> getAllPets () {
        List<PetModel> listPets = petRepository.findAll();

        List<PetResponseDTO> responseList = new ArrayList<>();
        for (PetModel petModel : listPets) {
            responseList.add(PetMapper.toResponse(petModel));
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @PutMapping("/pets/{id}")
    public ResponseEntity<PetResponseDTO> updatePet (@PathVariable(value = "id") Long id,
                                             @RequestBody @Valid PetRequestDTO petRequestDTO) {
        Optional<PetModel> petOptional = petRepository.findById(id);
        if (petOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        var petModel = petOptional.get();
        PetMapper.updatePet(petModel, petRequestDTO);

        var savedPet = petRepository.save(petModel);

        return ResponseEntity.status(HttpStatus.OK).body(PetMapper.toResponse(savedPet));
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

}
