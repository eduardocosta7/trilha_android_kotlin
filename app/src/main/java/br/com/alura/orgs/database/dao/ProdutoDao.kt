package br.com.alura.orgs.database.dao

import androidx.room.*
import br.com.alura.orgs.model.Produto

@Dao
interface ProdutoDao {

    @Query("SELECT * FROM PRODUTO")
    suspend fun buscaTodos() : List<Produto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(vararg produto: Produto)

    @Delete
    suspend fun deleta(vararg produto: Produto)

    @Query("SELECT * FROM PRODUTO WHERE ID = :id")
    suspend fun buscaPorId(id: Int) : Produto?

    @Query("SELECT * FROM PRODUTO ORDER BY nome desc")
    suspend fun selectNomDesc() : List<Produto>

    @Query("SELECT * FROM PRODUTO ORDER BY nome asc")
    suspend fun selectNomAsc() : List<Produto>

    @Query("SELECT * FROM PRODUTO ORDER BY descricao desc")
    suspend fun selectDesDesc() : List<Produto>

    @Query("SELECT * FROM PRODUTO ORDER BY descricao asc")
    suspend fun selectDesAsc() : List<Produto>

    @Query("SELECT * FROM PRODUTO ORDER BY valor desc")
    suspend fun selectValorDesc() : List<Produto>

    @Query("SELECT * FROM PRODUTO ORDER BY valor asc")
    suspend fun selectValorAsc() : List<Produto>
}