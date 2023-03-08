package br.com.empiricus.statusviajante.service.impl

import br.com.empiricus.statusviajante.model.Usuario
import br.com.empiricus.statusviajante.repository.UsuarioRepository
import br.com.empiricus.statusviajante.service.UsuarioService
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.regex.Pattern

@Service
@Transactional
class UsuarioServiceImpl(
    private val usuarioRepository: UsuarioRepository
): UsuarioService {

    private fun getUsername(): String {
        return SecurityContextHolder.getContext().authentication.principal.toString()
    }
    override fun getUsuarioByUsername(username: String): Usuario = usuarioRepository.findByUsuario(username)

    override fun saveUsuario(usuario: Usuario): Usuario = usuarioRepository.save(usuario)

    override fun updateUsuario(usuario: Usuario): Usuario {
        val user = usuarioRepository.findByUsuario(getUsername())
        if(!comparePasswords(usuario.senha,user.senha)){
            throw Exception("Senha inválido")
        }

        user.nome = usuario.nome
        user.usuario = usuario.usuario
        user.senha = user.senha
        user.email = usuario.email
        user.celular = usuario.celular

        return usuarioRepository.save(user)
    }


    override fun createUsuario(usuario: Usuario): Usuario {
        if(!validadorSenha.matcher(usuario.senha).matches()){
            throw Exception("Senha inválido")
        }
        usuario.senha = encoder.encode(usuario.senha)
        return usuarioRepository.save(usuario)
    }

    override fun deleteUsuarioById(id: Long) {
        usuarioRepository.deleteById(id)
    }

    fun comparePasswords(plainPassword: String, hashedPassword: String): Boolean {
        return BCrypt.checkpw(plainPassword, hashedPassword)
    }


    companion object {
        /**
         * Password must contain at least one digit [0-9].
         * Password must contain at least one lowercase Latin character [a-z].
         * Password must contain at least one uppercase Latin character [A-Z].
         * Password must contain at least one special character like ! @ # & ( ).
         * Password must contain a length of at least 8 characters and a maximum of 20 characters.
         */
        private const val PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$"
        private val encoder = BCryptPasswordEncoder()
        private val validadorSenha = Pattern.compile(PASSWORD_PATTERN)
    }
}