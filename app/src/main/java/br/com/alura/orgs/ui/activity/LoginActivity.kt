package br.com.alura.orgs.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityLoginBinding
import br.com.alura.orgs.preferences.dataStore
import br.com.alura.orgs.preferences.usuarioLogadoPreferences
import br.com.alura.orgs.util.vaiPara
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val usuarioDao by lazy {
        AppDatabase.instanciaDB(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        controles()
    }

    fun controles() {
        binding.apply {
            btnLogin.setOnClickListener {
                val usuario = edtUsuario.text.toString()
                val senha = edtSenha.text.toString()
                lifecycleScope.launch {
                    usuarioDao.autentica(usuario, senha)?.let { usuario ->
                        dataStore.edit { preferences ->
                            preferences[usuarioLogadoPreferences] = usuario.id
                        }
                        vaiPara(ListaProdutosActivity::class.java)
                    } ?: Toast.makeText(this@LoginActivity, "Falha na autentificação", Toast.LENGTH_SHORT).show()
                }
            }

            btnNovoCadastro.setOnClickListener {
                vaiPara(CadastroActivity::class.java)
            }
        }
    }
}