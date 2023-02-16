package br.com.empiricus.statusviajante.service

import br.com.empiricus.statusviajante.model.Usuario

interface UsuarioService {

    fun getUsuarioById(id: Long): Usuario
    fun getUsuarioByUsername(username: String): Usuario
    fun saveUsuario(usuario: Usuario): Usuario
    fun updateUsuario(usuario: Usuario): Usuario
    fun createUsuario(usuario: Usuario): Usuario
    fun deleteUsuarioById(id: Long)

}