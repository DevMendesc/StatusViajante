package br.com.empiricus.statusviajante.repository

import br.com.empiricus.statusviajante.model.GastosViagem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GastosViagemRepository: JpaRepository<GastosViagem, Long> {
    fun findByCategoriaContainingIgnoreCase(categoria: String): List<GastosViagem>
}