package br.com.empiricus.statusviajante.security

import br.com.empiricus.statusviajante.model.UserSecurity
import br.com.empiricus.statusviajante.repository.UsuarioRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserDetailsService(
    private val usuarioRepository: UsuarioRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails? {
            val user = usuarioRepository.findByUsuario(username)
            return UserSecurity(
                user.usuario,
                user.senha
            )
    }
}