package br.com.alura.orgs.database.dao

import androidx.room.*
import br.com.alura.orgs.model.Produto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdutoDao {

    @Query("SELECT * FROM PRODUTO")
    fun buscaTodos() : Flow<List<Produto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(vararg produto: Produto)

    @Query("DELETE FROM PRODUTO WHERE ID = :id")
    suspend fun deleta(id: Int)

    @Query("SELECT * FROM PRODUTO WHERE ID = :id")
    fun buscaPorId(id: Int) : Flow<Produto?>

    @Query("SELECT * FROM PRODUTO")
    suspend fun selectSemFiltro() : List<Produto>

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