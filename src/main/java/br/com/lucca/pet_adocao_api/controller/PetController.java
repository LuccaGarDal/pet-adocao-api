package br.com.lucca.pet_adocao_api.controller;

import br.com.lucca.pet_adocao_api.dtos.PetRequestDTO;
import br.com.lucca.pet_adocao_api.model.EnderecoModel;
import br.com.lucca.pet_adocao_api.model.PetModel;
import br.com.lucca.pet_adocao_api.repository.PetRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.Optional;

// POST(feito), GET, PUT, DELETE

@RestController
public class PetController {

    @Autowired
    PetRepository petRepository;

    @PostMapping("/pets")
    public ResponseEntity<PetModel> savePet (@RequestBody @Valid PetRequestDTO petRequestDTO) {
        var petModel = new PetModel();
        BeanUtils.copyProperties(petRequestDTO, petModel);
        EnderecoModel endereco = new EnderecoModel();
        BeanUtils.copyProperties(petRequestDTO.endereco(), endereco);
        petModel.setEndereco(endereco);
        return ResponseEntity.status(HttpStatus.CREATED).body(petRepository.save(petModel));
    }

    @GetMapping("/pets/{id}")
    public ResponseEntity<Object> getPet (@PathVariable(value = "id") Long id) {
        Optional<PetModel> petModel = petRepository.findById(id);
        if (petModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(petModel.get());
    }

    @GetMapping("/pets")
    public ResponseEntity<List<PetModel>> getAllPets () {
        List<PetModel> listPets = petRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(listPets);
    }

    @PutMapping("/pets/{id}")
    public ResponseEntity<Object> updatePet (@PathVariable(value = "id") Long id,
                                             @RequestBody PetRequestDTO petRequestDTO) {
        Optional<PetModel> petModel0 = petRepository.findById(id);
        if (petModel0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet not found");
        }
        var petModel = petModel0.get();
        BeanUtils.copyProperties(petRequestDTO, petModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(petRepository.save(petModel));
    }
}
