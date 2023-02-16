package br.com.empiricus.statusviajante.service.impl

import br.com.empiricus.statusviajante.model.GastosViagem
import br.com.empiricus.statusviajante.repository.GastosViagemRepository
import br.com.empiricus.statusviajante.repository.ViagemRepository
import br.com.empiricus.statusviajante.service.GastosViagemService
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class GastosViagemServiceImpl(
    private val gastosViagemRepository: GastosViagemRepository,
    private val viagemRepository: ViagemRepository
): GastosViagemService {
    override fun getAllGastosByViagem(id: Long): List<GastosViagem> = viagemRepository.findById(id).orElseThrow().gastosViagems

    override fun getGastosByCategoria(categoria: String): List<GastosViagem> = gastosViagemRepository.findByCategoriaContainingIgnoreCase(categoria)

    override fun saveGastos(gastos: GastosViagem, id: Long): GastosViagem {
        gastos.viagem = viagemRepository.findById(id).orElseThrow()
        return gastosViagemRepository.save(gastos)
    }

    override fun updateGastos(gastos: GastosViagem, id: Long): GastosViagem {
        val gastooAtt = gastosViagemRepository.findById(id).orElseThrow()
        gastooAtt.apply {
            dataGasto = gastos.dataGasto
            valorGasto = gastos.valorGasto
            moeda = gastos.moeda
            descricaoGasto = gastos.descricaoGasto
            categoria = gastos.categoria
        }
        return gastosViagemRepository.save(gastooAtt)
    }

    override fun deleteGastosById(id: Long) = gastosViagemRepository.deleteById(id)
}