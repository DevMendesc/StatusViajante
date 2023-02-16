package br.com.empiricus.statusviajante.controller

import br.com.empiricus.statusviajante.model.Usuario
import br.com.empiricus.statusviajante.service.UsuarioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*



@RestController
@RequestMapping("cadastrar")
class CadastroUsuariosController(
    private val usuarioService: UsuarioService
) {
    @PostMapping
    fun cadastrarUsuario(@RequestBody usuario: Usuario): ResponseEntity<Usuario> = ResponseEntity.ok(usuarioService.createUsuario(usuario))
}