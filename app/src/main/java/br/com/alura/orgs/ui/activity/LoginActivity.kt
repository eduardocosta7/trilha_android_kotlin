package br.com.alura.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.databinding.ActivityLoginBinding
import br.com.alura.orgs.util.vaiPara

class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
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

                vaiPara(ListaProdutosActivity::class.java)
            }

            btnNovoCadastro.setOnClickListener {
                vaiPara(CadastroActivity::class.java)
            }
        }
    }
}