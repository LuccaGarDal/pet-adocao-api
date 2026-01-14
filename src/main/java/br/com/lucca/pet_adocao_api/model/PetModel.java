package br.com.lucca.pet_adocao_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table (name="pets")
public class PetModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeCompleto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPet tipoPet;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SexoPet sexoPet;

    private Integer idade;
    private Double peso;
    private String raca;

    @Embedded
    private EnderecoModel endereco;

    @Column(nullable = false)
    private LocalDateTime dataCadastro;

    @PrePersist
    public void prePersist() {
        this.dataCadastro = LocalDateTime.now();
    }


}
