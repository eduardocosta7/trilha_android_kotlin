package br.com.alura.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.databinding.ActivityDetalheBinding
import br.com.alura.orgs.extensions.tentaCarregarImagem

class DetalheActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetalheBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        controles()
    }

    private fun controles() {
        intent?.apply {
            extras?.apply {
                binding.mImageView.tentaCarregarImagem(getString("imagem"))
                binding.mTextViewNome.text = getString("nome")
                binding.mTextViewDescricao.text = getString("descricao")
                binding.mTextValor.text = getString("valor")
            }
        }
    }
}