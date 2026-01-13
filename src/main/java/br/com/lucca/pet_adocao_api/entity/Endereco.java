package br.com.lucca.pet_adocao_api.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Endereco {
    private String rua;
    private String numero;
    private String cidade;
}
