package br.com.empiricus.statusviajante.service.impl

import br.com.empiricus.statusviajante.model.Viagem
import br.com.empiricus.statusviajante.repository.UsuarioRepository
import br.com.empiricus.statusviajante.repository.ViagemRepository
import br.com.empiricus.statusviajante.service.ViagemService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Service
@Transactional
class ViagemServiceImpl(
    private val viagemRepository: ViagemRepository,
    private val usuarioRepository: UsuarioRepository
): ViagemService {

    private fun getUsername(): String {
        return SecurityContextHolder.getContext().authentication.principal.toString()
    }
    override fun getAllViagensByUsuario(usuario: String): List<Viagem> = usuarioRepository.findByUsuario(usuario).viagens

    override fun getAllViagensByUsuarioId(id: Long): List<Viagem> = usuarioRepository.findById(id).orElseThrow().viagens

    override fun getViagemById(id: Long): Viagem = viagemRepository.findById(id).orElseThrow()

    override fun getViagensByNome(nome: String): List<Viagem> = viagemRepository.findByNomeContainingIgnoreCase(nome)

    override fun saveViagem(viagem: Viagem): Viagem {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        viagem.usuario = usuarioRepository.findByUsuario(getUsername())
        viagem.diasDeViagem = (ChronoUnit.DAYS.between(LocalDate.parse(viagem.dataIda, formatter), LocalDate.parse(viagem.dataVolta, formatter)) +1).toInt()
        viagem.orcamentoDiario = (viagem.orcamento / viagem.diasDeViagem)
        viagem.orcamentoRestante = (viagem.orcamento - viagem.gastoTotal)

        return viagemRepository.save(viagem)
        }

    override fun updateViagem(viagem: Viagem, id: Long): Viagem {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val viagemAtt: Viagem = viagemRepository.findById(id).orElseThrow()
        viagem.diasDeViagem = (ChronoUnit.DAYS.between(LocalDate.parse(viagem.dataIda, formatter), LocalDate.parse(viagem.dataVolta, formatter)) +1).toInt()
        viagem.orcamentoRestante = (viagem.orcamento - viagem.gastoTotal)
        viagemAtt.apply {
            nome = viagem.nome
            gastoTotal = viagem.gastoTotal
            origem = viagem.origem
            destino = viagem.destino
            dataIda = viagem.dataIda
            dataVolta = viagem.dataVolta
            diasDeViagem = viagem.diasDeViagem
            orcamento = viagem.orcamento
            orcamentoDiario = viagem.orcamento / viagem.diasDeViagem
            orcamentoRestante = viagem.orcamentoRestante
            qtdPessoas = viagem.qtdPessoas
            descricaoViagem = viagem.descricaoViagem
        }
        return viagemRepository.save(viagemAtt)
    }

    override fun deleteViagemById(id: Long) = viagemRepository.deleteById(id)
}