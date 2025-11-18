package org.acme;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Treinador extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank(message = "O nome não pode ser vazio")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    public String nome;

    @Past(message = "A data de nascimento deve ser no passado")
    public LocalDate nascimento;

    @NotBlank(message = "A nacionalidade é obrigatória")
    @Size(max = 80)
    public String nacionalidade;

    // One-to-One: um treinador tem uma biografia
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "biografia_id")
    public BiografiaTreinador biografia;

    // One-to-Many: um treinador pode ter vários lutadores
    @OneToMany(mappedBy = "treinador", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    public List<Lutador> lutadores = new ArrayList<>();

    public Treinador() {}

    public Treinador(Long id, String nome, LocalDate nascimento, String nacionalidade, BiografiaTreinador biografia) {
        this.id = id;
        this.nome = nome;
        this.nascimento = nascimento;
        this.nacionalidade = nacionalidade;
        this.biografia = biografia;
    }
}