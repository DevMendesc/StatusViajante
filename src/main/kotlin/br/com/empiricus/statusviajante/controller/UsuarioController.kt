package br.com.empiricus.statusviajante.controller

import br.com.empiricus.statusviajante.model.Usuario
import br.com.empiricus.statusviajante.service.UsuarioService
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("usuario")
class UsuarioController(
    private val usuarioService: UsuarioService
) {

    fun getUsername(): String {
        return SecurityContextHolder.getContext().authentication.principal.toString()
    }

    @GetMapping
    fun getUsuarioByUsername(): ResponseEntity<Usuario> = ResponseEntity.ok(usuarioService.getUsuarioByUsername(getUsername()))

    @PutMapping
    fun updateUsuario(@RequestBody usuario: Usuario): ResponseEntity<Usuario> = ResponseEntity.ok(usuarioService.updateUsuario(usuario))


    @DeleteMapping("/{id}")
    fun deleteUsuario(@PathVariable id: Long) = ResponseEntity.ok(usuarioService.deleteUsuarioById(id))
}