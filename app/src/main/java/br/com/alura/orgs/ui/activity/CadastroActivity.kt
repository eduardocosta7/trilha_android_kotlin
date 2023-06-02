package br.com.alura.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityCadastroBinding
import br.com.alura.orgs.model.Usuario
import kotlinx.coroutines.launch

class CadastroActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCadastroBinding.inflate(layoutInflater)
    }
    private val dao by lazy {
        AppDatabase.instanciaDB(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        controles()
    }

    fun controles() {
        title = "Cadastrar usuário"
        binding.apply {
            btnCadastrar.setOnClickListener {
                val usuario = criaNovoUsuario()

                lifecycleScope.launch {
                    try {
                        dao.salva(usuario)
                        finish()
                    } catch (e: Exception) {
                        Log.i("CriaUsuario", "${e.message}")
                        Toast.makeText(this@CadastroActivity, "Falha ao cadastrar usuário", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun ActivityCadastroBinding.criaNovoUsuario(): Usuario {
        val nome = edtNome.text.toString()
        val usuario = edtUsuario.text.toString()
        val senha = edtSenha.text.toString()

        return Usuario(nome, usuario, senha)
    }
}