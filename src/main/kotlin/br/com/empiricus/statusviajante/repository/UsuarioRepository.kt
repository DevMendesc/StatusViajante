package br.com.empiricus.statusviajante.repository

import br.com.empiricus.statusviajante.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsuarioRepository: JpaRepository<Usuario, Long> {
    fun findByUsuario(usuario: String): Usuario
}