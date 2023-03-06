package br.com.empiricus.statusviajante.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


@Entity
@Table
class Viagem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @field:NotNull
    @field:Size(min = 3, max = 100)
    var nome: String,

    @field:NotNull
    @field:Size(min = 3, max = 100)
    var origem: String,

    @field:NotNull
    @field:Size(min = 3, max = 100)
    var destino: String,

    @field:NotNull
    var dataIda: String,

    @field:NotNull
    var dataVolta: String,

    var diasDeViagem: Int,

    @field:NotNull
    var orcamento: Double,

    var orcamentoDiario: Double,

    @field:NotNull
    var orcamentoRestante: Double,

    @field:NotNull
    var qtdPessoas: Int,

    @field:NotNull
    @field:Size(min = 3, max = 200)
    var descricaoViagem: String,

    @field:NotNull
    var gastoTotal: Double = 0.0

    )
{
    @OneToMany(mappedBy = "viagem", cascade = [(CascadeType.ALL)], orphanRemoval = true)
    @JsonIgnore
    var gastosViagems: List<GastosViagem> = listOf()

    @ManyToOne
    @JsonIgnore
    lateinit var usuario: Usuario
}