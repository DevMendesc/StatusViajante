package br.com.empiricus.statusviajante.repository

import br.com.empiricus.statusviajante.model.Viagem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ViagemRepository: JpaRepository<Viagem, Long> {
    fun findByNomeContainingIgnoreCase(nome: String): List<Viagem>
}