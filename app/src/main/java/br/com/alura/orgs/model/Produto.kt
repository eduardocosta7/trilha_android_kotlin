package br.com.alura.orgs.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity
data class Produto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val descricao: String,
    val valor: BigDecimal,
    val imagem: String? = null
) {
    constructor(nome: String, descricao: String, valor: BigDecimal, imagem: String?) :
            this(0, nome, descricao, valor, imagem)
}
