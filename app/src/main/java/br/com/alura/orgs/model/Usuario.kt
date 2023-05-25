package br.com.alura.orgs.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Usuario(
    @PrimaryKey
    val nome: String,
    val email: String,
    val senha: String
)
