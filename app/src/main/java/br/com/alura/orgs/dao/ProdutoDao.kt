package br.com.alura.orgs.dao

import br.com.alura.orgs.model.Produto

class ProdutoDao {

    fun adiciona(produto: Produto){
        Companion.produtos.add(produto)
    }

    fun buscaTodos() : List<Produto> {
        return Companion.produtos.toList()
    }

    companion object {
        private val produtos = mutableListOf<Produto>()
    }
}