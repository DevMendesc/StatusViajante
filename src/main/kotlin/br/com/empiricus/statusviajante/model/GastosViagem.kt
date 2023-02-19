package br.com.empiricus.statusviajante.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.util.*


@Entity
@Table
class GastosViagem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @field:NotBlank
    var dataGasto: Date,

    @field:NotBlank
    var valorGasto: Double,

    @field:NotBlank
    var moeda: String,

    @field:NotBlank
    var descricaoGasto: String,

    @field:NotBlank
    var categoria: String

) {
    @ManyToOne
    @JsonIgnore
    lateinit var viagem: Viagem
}