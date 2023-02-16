package br.com.empiricus.statusviajante.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
@Table
class Usuario(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,

    @org.jetbrains.annotations.NotNull
    @NotBlank
    @Size(min = 3, max = 50)
    var nome: String,

    @NotNull
    @NotBlank
    @Size(min = 5, max = 20)
    var usuario: String,

    @NotNull
    @NotBlank
    @Size(min = 5, max = 50)
    var senha: String,

    @NotNull
    @NotBlank
    @Size(min = 10, max = 50)
    var email: String,

    @NotNull
    @NotBlank
    @Size(min = 11, max = 11)
    var celular: String,

    @OneToMany(mappedBy = "usuario", cascade = [(CascadeType.ALL)], orphanRemoval = true)
    @JsonIgnore
    var viagens: List<Viagem> = listOf()
)