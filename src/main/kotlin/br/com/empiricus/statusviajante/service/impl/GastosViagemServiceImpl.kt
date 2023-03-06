package br.com.empiricus.statusviajante.service.impl

import br.com.empiricus.statusviajante.model.GastosViagem
import br.com.empiricus.statusviajante.repository.GastosViagemRepository
import br.com.empiricus.statusviajante.repository.UsuarioRepository
import br.com.empiricus.statusviajante.repository.ViagemRepository
import br.com.empiricus.statusviajante.service.GastosViagemService
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.text.SimpleDateFormat

@Service
@Transactional
class GastosViagemServiceImpl(
    private val gastosViagemRepository: GastosViagemRepository,
    private val viagemRepository: ViagemRepository,
    private val usuarioRepository: UsuarioRepository
): GastosViagemService {

    fun getUsername(): String {
        return SecurityContextHolder.getContext().authentication.principal.toString()
    }

    override fun getAllGastosByViagem(id: Long): List<GastosViagem> {
        val usuario = usuarioRepository.findByUsuario(getUsername()).viagens
        var gastos = listOf<GastosViagem>()
        usuario.forEach {
            when(it.id){
                id -> {gastos = it.gastosViagems}
            }
        }
        return gastos
    }

    override fun getGastosById(id: Long): GastosViagem = gastosViagemRepository.findById(id).orElseThrow()

    override fun getGastosByCategoria(id: Long, categoria: String): List<GastosViagem> {
        val viagem = viagemRepository.findById(id).orElseThrow()
        val listGastos = mutableListOf<GastosViagem>()
        viagem.gastosViagems.forEach {
            when(it.categoria){
                categoria -> {listGastos += it}
            }
        }
        return listGastos
    }

    override fun saveGastos(gastos: GastosViagem, id: Long): GastosViagem {
        val viagem = viagemRepository.findById(id).orElseThrow()
        gastos.viagem = viagem
        viagem.gastoTotal += gastos.valorGasto
        viagem.orcamentoRestante -= gastos.valorGasto
        return gastosViagemRepository.save(gastos)
    }

    override fun updateGastos(gastos: GastosViagem, id: Long): GastosViagem {
        val gastooAtt = gastosViagemRepository.findById(id).orElseThrow()
        gastooAtt.viagem.gastoTotal -= gastooAtt.valorGasto
        gastooAtt.viagem.orcamentoRestante += gastooAtt.valorGasto
        gastooAtt.apply {
            dataGasto = gastos.dataGasto
            valorGasto = gastos.valorGasto
            moeda = gastos.moeda
            descricaoGasto = gastos.descricaoGasto
            categoria = gastos.categoria
        }
        gastooAtt.viagem.gastoTotal += gastos.valorGasto
        gastooAtt.viagem.orcamentoRestante -= gastos.valorGasto
        return gastosViagemRepository.save(gastooAtt)
    }

    override fun deleteGastosById(id: Long) {
        val gasto = gastosViagemRepository.findById(id).orElseThrow()
        gasto.viagem.gastoTotal -= gasto.valorGasto
        gasto.viagem.orcamentoRestante += gasto.valorGasto
        gastosViagemRepository.deleteById(id)
    }
}