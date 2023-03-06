package br.com.empiricus.statusviajante.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


@Entity
@Table
class GastosViagem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @field:NotNull
    var dataGasto: String,

    @field:NotNull
    var valorGasto: Double,

    @field:NotNull
    var moeda: String,

    @field:NotNull
    var descricaoGasto: String,

    @field:NotNull
    var categoria: String

) {

    @ManyToOne
    @JsonIgnore
    lateinit var viagem: Viagem
}