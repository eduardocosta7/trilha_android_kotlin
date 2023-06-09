package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityLoginBinding
import br.com.alura.orgs.preferences.dataStore
import br.com.alura.orgs.preferences.usuarioLogadoPreferences
import br.com.alura.orgs.util.toast
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
                autentica(usuario, senha)
            }

            btnNovoCadastro.setOnClickListener {
                vaiPara(CadastroActivity::class.java)
            }
        }
    }

    private fun autentica(usuario: String, senha: String) {
        lifecycleScope.launch {
            usuarioDao.autentica(usuario, senha)?.let { usuario ->
                dataStore.edit { preferences ->
                    preferences[usuarioLogadoPreferences] = usuario.id
                }
                vaiPara(ListaProdutosActivity::class.java) {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                finish()
            } ?: toast("Falha na autentificação")
        }
    }
}