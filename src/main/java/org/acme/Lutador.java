package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Lutador extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank(message = "O nome não pode ser vazio")
    @Size(min = 1, max = 200)
    public String nome;

    @NotBlank(message = "A história é obrigatória")
    @Size(max = 2000)
    public String historia;

    @Min(value = 1900, message = "Ano de nascimento inválido")
    public int anoNascimento;

    @DecimalMin(value = "0.0", inclusive = true, message = "Ranking mínimo é 0.0")
    @DecimalMax(value = "10.0", inclusive = true, message = "Ranking máximo é 10.0")
    public double ranking;

    @Min(value = 0, message = "Número de vitórias não pode ser negativo")
    public int vitorias;

    @Enumerated(EnumType.STRING)
    public StatusCarreira statusCarreira;

    // Many-to-One: vários lutadores para um treinador
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "treinador_id")
    public Treinador treinador;

    // Many-to-Many: lutadores - categorias de peso
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "lutador_categoriadepeso",
            joinColumns = @JoinColumn(name = "lutador_id"),
            inverseJoinColumns = @JoinColumn(name = "categoriadepeso_id")
    )
    public Set<CategoriaDePeso> categoriasDePeso = new HashSet<>();

    public Lutador() {}

    public Lutador(Long id, String nome, String historia, int anoNascimento, double ranking, int vitorias, StatusCarreira statusCarreira) {
        this.id = id;
        this.nome = nome;
        this.historia = historia;
        this.anoNascimento = anoNascimento;
        this.ranking = ranking;
        this.vitorias = vitorias;
        this.statusCarreira = statusCarreira;
    }
}