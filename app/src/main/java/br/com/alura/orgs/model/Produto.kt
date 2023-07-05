package br.com.alura.orgs.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.math.BigDecimal

@Entity
data class Produto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var nome: String,
    var descricao: String,
    var valor: BigDecimal,
    var imagem: String? = null,
    var idUsuario: Int? = null
) : Serializable {
    constructor(nome: String, descricao: String, valor: BigDecimal, imagem: String?) :
            this(0, nome, descricao, valor, imagem)
}
