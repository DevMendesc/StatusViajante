package br.com.empiricus.statusviajante.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
@Table
class Viagem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @org.jetbrains.annotations.NotNull
    @NotBlank
    @Size(min = 3, max = 100)
    var nome: String,

    @NotNull
    @NotBlank
    @Size(min = 3, max = 100)
    var origem: String,

    @NotNull
    @NotBlank
    @Size(min = 3, max = 100)
    var destino: String,

    @NotNull
    @NotBlank
    @Size(min = 2, max = 100)
    var moeda: String,

    @NotNull
    @NotBlank
    var dataIda: Date,

    @NotNull
    @NotBlank
    var dataVolta: Date,

    @NotNull
    @NotBlank
    var orcamento: Double,

    @NotNull
    @NotBlank
    var qtdPessoas: Int,

    @NotNull
    @NotBlank
    @Size(min = 3, max = 200)
    var descricaoViagem: String,

)
{
    @OneToMany(mappedBy = "viagem", cascade = [(CascadeType.ALL)], orphanRemoval = true)
    @JsonIgnore
    var gastosViagems: List<GastosViagem> = listOf()

    @ManyToOne
    @JsonIgnore
    lateinit var usuario: Usuario
}