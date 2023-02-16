package br.com.empiricus.statusviajante.controller

import br.com.empiricus.statusviajante.model.GastosViagem
import br.com.empiricus.statusviajante.service.GastosViagemService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("gastos")
class GastosViagemController(
    private val gastosViagemService: GastosViagemService
) {


    @GetMapping("/{id}")
    fun getAllGastosByViagem(@PathVariable id: Long): ResponseEntity<List<GastosViagem>> = ResponseEntity.ok(gastosViagemService.getAllGastosByViagem(id))

    @GetMapping("/categoria/{categoria}")
    fun getGastosByCategoria(@PathVariable categoria: String): ResponseEntity<List<GastosViagem>> = ResponseEntity.ok(gastosViagemService.getGastosByCategoria(categoria))

    @PostMapping("/{id}")
    fun saveGastosViagem(@PathVariable id: Long, @RequestBody gastosViagem: GastosViagem): ResponseEntity<GastosViagem> = ResponseEntity.ok(gastosViagemService.saveGastos(gastos = gastosViagem, id = id))

    @PutMapping("/{id}")
    fun updateGastosViagem(@RequestBody gastosViagem: GastosViagem, @PathVariable id: Long): ResponseEntity<GastosViagem> = ResponseEntity.ok(gastosViagemService.updateGastos(gastosViagem, id))

    @DeleteMapping("/{id}")
    fun deleteGastosViagem(@PathVariable id: Long): ResponseEntity<Unit> = ResponseEntity.ok(gastosViagemService.deleteGastosById(id))

}
