package br.com.lucca.pet_adocao_api.model.pet;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class EnderecoModel {
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;

}
