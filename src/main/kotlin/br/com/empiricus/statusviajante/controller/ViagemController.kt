package br.com.empiricus.statusviajante.controller

import br.com.empiricus.statusviajante.model.GastosViagem
import br.com.empiricus.statusviajante.model.Viagem
import br.com.empiricus.statusviajante.service.GastosViagemService
import br.com.empiricus.statusviajante.service.ViagemService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("viagens")
class ViagemController(
    private val viagemService: ViagemService,
) {

    fun getUsername(): String {
        return SecurityContextHolder.getContext().authentication.principal.toString()
    }

    @GetMapping
    fun getAllViagens(): ResponseEntity<List<Viagem>> = ResponseEntity.ok(viagemService.getAllViagensByUsuario(getUsername()))

    @GetMapping("/user/{id}")
    fun getAllViagensByUserId(@PathVariable id: Long): ResponseEntity<List<Viagem>> = ResponseEntity.ok(viagemService.getAllViagensByUsuarioId(id))

    @GetMapping("/{id}")
    fun getViagemById(@PathVariable id: Long): ResponseEntity<Viagem> = ResponseEntity.ok(viagemService.getViagemById(id))

    @GetMapping("/nome/{nome}")
    fun getViagensByName(@PathVariable nome: String): ResponseEntity<List<Viagem>> = ResponseEntity.ok(viagemService.getViagensByNome(nome))

    @PostMapping
    fun saveViagem(@RequestBody viagem: Viagem): ResponseEntity<Viagem> = ResponseEntity.ok(viagemService.saveViagem(viagem))

    @PutMapping("/{id}")
    fun updateViagem(@RequestBody viagem: Viagem, @PathVariable id: Long): ResponseEntity<Viagem> = ResponseEntity.ok(viagemService.updateViagem(viagem,id))

    @DeleteMapping("/{id}")
    fun deleteViagem(@PathVariable id: Long): ResponseEntity<Unit> = ResponseEntity.ok(viagemService.deleteViagemById(id))
}