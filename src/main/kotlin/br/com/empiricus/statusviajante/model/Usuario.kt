package br.com.empiricus.statusviajante.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table
class Usuario(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,

    @field:NotNull
    @field:Size(min = 3, max = 50)
    var nome: String,


    @field:NotNull
    @field:Size(min = 5, max = 20)
    @Column(unique = true)
    var usuario: String,

    @field:NotNull
    @field:Size(min = 5, max = 100)
    var senha: String,

    @field:NotNull
    @field:Size(min = 10, max = 50)
    @Column(unique = true)
    var email: String,

    @field:NotNull
    @field:Size(min = 11, max = 11)
    var celular: String,

    @OneToMany(mappedBy = "usuario", cascade = [(CascadeType.ALL)], orphanRemoval = true)
    @JsonIgnore
    var viagens: List<Viagem> = listOf()
)