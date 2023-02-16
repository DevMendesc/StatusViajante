package br.com.empiricus.statusviajante.service.impl

import br.com.empiricus.statusviajante.model.Usuario
import br.com.empiricus.statusviajante.repository.UsuarioRepository
import br.com.empiricus.statusviajante.service.UsuarioService
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UsuarioServiceImpl(
    private val usuarioRepository: UsuarioRepository
): UsuarioService {

    private fun getUsername(): String {
        return SecurityContextHolder.getContext().authentication.principal.toString()
    }
    override fun getUsuarioById(id: Long): Usuario = usuarioRepository.findById(id).orElseThrow {NotFoundException()}

    override fun getUsuarioByUsername(username: String): Usuario = usuarioRepository.findByUsuario(username)

    override fun saveUsuario(usuario: Usuario): Usuario = usuarioRepository.save(usuario)

    override fun updateUsuario(usuario: Usuario): Usuario {
        val user = usuarioRepository.findByUsuario(getUsername())
        val encoder = BCryptPasswordEncoder()

        user.nome = usuario.nome
        user.usuario = usuario.usuario
        user.senha = encoder.encode(usuario.senha)
        user.email = usuario.email
        user.celular = usuario.celular

        return usuarioRepository.save(user)
    }

    override fun createUsuario(usuario: Usuario): Usuario {
        val encoder = BCryptPasswordEncoder()
        usuario.senha = encoder.encode(usuario.senha)
        return usuarioRepository.save(usuario)
    }

    override fun deleteUsuarioById(id: Long) {
        usuarioRepository.deleteById(id)
    }
}