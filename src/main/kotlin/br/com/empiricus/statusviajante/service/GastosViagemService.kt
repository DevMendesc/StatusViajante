package br.com.empiricus.statusviajante.service

import br.com.empiricus.statusviajante.model.GastosViagem

interface GastosViagemService {

    fun getAllGastosByViagem(id: Long) : List<GastosViagem>
    fun getGastosById(id: Long) : GastosViagem
    fun getGastosByCategoria(id: Long, categoria: String) : List<GastosViagem>
    fun saveGastos(gastos: GastosViagem, id: Long): GastosViagem
    fun updateGastos(gastos: GastosViagem, id: Long): GastosViagem
    fun deleteGastosById(id: Long)

}