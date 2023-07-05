package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.model.Usuario
import br.com.alura.orgs.preferences.dataStore
import br.com.alura.orgs.preferences.usuarioLogadoPreferences
import br.com.alura.orgs.util.vaiPara
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

abstract class AppBaseActivity : AppCompatActivity() {

    private val usuarioDao by lazy {
        AppDatabase.instanciaDB(this).usuarioDao()
    }

    private val _usuario: MutableStateFlow<Usuario?> = MutableStateFlow(null)
    protected val usuario: StateFlow<Usuario?> = _usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            verificaUsuarioLogado()
        }
    }

    private suspend fun verificaUsuarioLogado() {
        dataStore.data.collect { preferences ->
            preferences[usuarioLogadoPreferences]?.let { usuarioId ->
                buscaUsuario(usuarioId)
            } ?: vaiParaLogin()
        }
    }

    private suspend fun buscaUsuario(usuarioId: Int) : Usuario? {
        return usuarioDao
            .buscaPorId(usuarioId)
            .firstOrNull()
            .also {
                _usuario.value = it
            }
    }

    private fun vaiParaLogin() {
        vaiPara(LoginActivity::class.java) {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        finish()
    }

    protected fun deslogaUsuario() {
        lifecycleScope.launch {
            dataStore.edit { preferences ->
                preferences.remove(usuarioLogadoPreferences)
            }
        }
        vaiParaLogin()
    }
}