package br.com.alura.orgs.database.dao

import androidx.room.*
import br.com.alura.orgs.model.Produto

@Dao
interface ProdutoDao {

    @Query("SELECT * FROM PRODUTO")
    fun buscaTodos() : List<Produto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun salva(vararg produto: Produto)

    @Delete
    fun deleta(vararg produto: Produto)

    @Query("SELECT * FROM PRODUTO WHERE ID = :id")
    fun buscaPorId(id: Int) : Produto?

    @Query("SELECT * FROM PRODUTO ORDER BY nome desc")
    fun selectNomDesc() : List<Produto>

    @Query("SELECT * FROM PRODUTO ORDER BY nome asc")
    fun selectNomAsc() : List<Produto>

    @Query("SELECT * FROM PRODUTO ORDER BY descricao desc")
    fun selectDesDesc() : List<Produto>

    @Query("SELECT * FROM PRODUTO ORDER BY descricao asc")
    fun selectDesAsc() : List<Produto>

    @Query("SELECT * FROM PRODUTO ORDER BY valor desc")
    fun selectValorDesc() : List<Produto>

    @Query("SELECT * FROM PRODUTO ORDER BY valor asc")
    fun selectValorAsc() : List<Produto>
}