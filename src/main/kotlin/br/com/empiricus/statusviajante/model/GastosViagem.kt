package br.com.empiricus.statusviajante.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table
class GastosViagem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @NotNull
    @NotBlank
    var dataGasto: Date,

    @NotNull
    @NotBlank
    var valorGasto: Double,

    @org.jetbrains.annotations.NotNull
    @NotBlank
    var moeda: String,

    @NotNull
    @NotBlank
    var descricaoGasto: String,

    @NotNull
    var categoria: String

) {
    @ManyToOne
    @JsonIgnore
    lateinit var viagem: Viagem
}