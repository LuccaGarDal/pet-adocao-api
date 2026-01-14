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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// POST, GET, PUT, DELETE

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

}
