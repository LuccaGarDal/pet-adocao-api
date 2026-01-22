package br.com.lucca.pet_adocao_api.repository;

import br.com.lucca.pet_adocao_api.model.pet.PetModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<PetModel, Long> {
}
