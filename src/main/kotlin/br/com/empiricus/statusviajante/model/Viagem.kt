package br.com.empiricus.statusviajante.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull
import java.time.LocalDate
import java.time.temporal.ChronoUnit


@Entity
@Table
class Viagem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @field:NotBlank
    @field:Size(min = 3, max = 100)
    var nome: String,

    @field:NotBlank
    @field:Size(min = 3, max = 100)
    var origem: String,

    @field:NotBlank
    @field:Size(min = 3, max = 100)
    var destino: String,

    @field:NotBlank
    var dataIda: LocalDate,

    @field:NotBlank
    var dataVolta: LocalDate,

    @field:NotBlank
    var diasDeViagem: Int = (ChronoUnit.DAYS.between(dataIda, dataVolta) +1).toInt(),

    @field:NotBlank
    var orcamento: Double,

    @field:NotBlank
    var orcamentoDiario: Double = (orcamento / diasDeViagem),

    @field:NotBlank
    var qtdPessoas: Int,

    @field:NotBlank
    @field:Size(min = 3, max = 200)
    var descricaoViagem: String,

    @field:NotBlank
    var gastoTotal: Double = 0.0,

)
{
    @OneToMany(mappedBy = "viagem", cascade = [(CascadeType.ALL)], orphanRemoval = true)
    @JsonIgnore
    var gastosViagems: List<GastosViagem> = listOf()

    @ManyToOne
    @JsonIgnore
    lateinit var usuario: Usuario
}