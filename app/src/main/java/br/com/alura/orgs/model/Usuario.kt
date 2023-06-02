package br.com.alura.orgs.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val usuario: String,
    val senha: String
) {
    constructor(nome: String, usuario: String, senha: String) : this(
        id = 0,
        nome = nome,
        usuario = usuario,
        senha = senha
    )
}
