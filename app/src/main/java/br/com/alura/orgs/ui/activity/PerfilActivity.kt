package br.com.alura.orgs.ui.activity

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.databinding.ActivityPerfilBinding
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class PerfilActivity : AppBaseActivity() {

    private val binding by lazy {
        ActivityPerfilBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.controles()
    }

    private fun ActivityPerfilBinding.controles() {
        lifecycleScope.launch {
            usuario.filterNotNull().collect {
                txtNomeUsuario.text = it.nome
            }
        }

        btnDeslogar.setOnClickListener {
            it.isClickable = false
            deslogaUsuario()
        }
    }

}