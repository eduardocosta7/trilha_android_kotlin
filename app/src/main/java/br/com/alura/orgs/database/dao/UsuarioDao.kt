package br.com.alura.orgs.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.alura.orgs.model.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Insert
    suspend fun salva(vararg usuario: Usuario)

    @Query("SELECT * FROM USUARIO WHERE usuario = :usuario AND senha = :senha")
    suspend fun autentica(usuario: String, senha: String) : Usuario?

    @Query("SELECT * FROM USUARIO WHERE id = :id")
    fun buscaPorId(id: Int): Flow<Usuario?>

}