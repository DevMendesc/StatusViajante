package br.com.empiricus.statusviajante.service

import br.com.empiricus.statusviajante.model.Viagem

interface ViagemService {

    fun getAllViagensByUsuario(usuario: String): List<Viagem>

    fun getAllViagensByUsuarioId(id:Long): List<Viagem>

    fun getViagemById(id: Long): Viagem

    fun getViagensByNome(nome: String): List<Viagem>

    fun saveViagem(viagem: Viagem): Viagem

    fun updateViagem(viagem: Viagem, id: Long): Viagem

    fun deleteViagemById(id: Long)
}