package br.com.alura.orgs.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.R
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_remover -> {

            }
            R.id.menu_editar -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun controles() {
        binding.apply {
            intent?.apply {
                extras?.apply {
                    mImageView.tentaCarregarImagem(getString("imagem"))
                    mTextViewNome.text = getString("nome")
                    mTextViewDescricao.text = getString("descricao")
                    mTextValor.text = getString("valor")
                }
            }
        }
    }
}