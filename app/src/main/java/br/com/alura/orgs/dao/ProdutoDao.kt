package br.com.alura.orgs.dao

import br.com.alura.orgs.model.Produto
import java.math.BigDecimal

class ProdutoDao {

    fun adiciona(produto: Produto) {
        produtos.add(produto)
    }

    fun buscaTodos(): List<Produto> {
        return produtos.toList()
    }

    companion object {
        private val produtos = mutableListOf(
            Produto(
                nome = "Cesta de frutas",
                descricao = "Maçã, laranja e banana",
                valor = BigDecimal("19.99")
            )
        )
    }
}