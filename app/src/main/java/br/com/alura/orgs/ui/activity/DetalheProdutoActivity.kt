package br.com.alura.orgs.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.R
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityDetalheBinding
import br.com.alura.orgs.extensions.tentaCarregarImagem
import br.com.alura.orgs.model.Produto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetalheProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetalheBinding.inflate(layoutInflater)
    }

    private val produtoDao by lazy {
        AppDatabase.instanciaDB(this).produtoDao()
    }

    private var produtoId: Int = 0
    private var usuarioProduto: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        controles()
    }

    @SuppressLint("SetTextI18n")
    private fun controles() {
        intent?.extras?.apply {
            produtoId = getInt("PRODUTO_ID", 0)
            usuarioProduto = getBoolean("USUARIO_PRODUTO", false)
        }

        lifecycleScope.launch {
            produtoDao.buscaPorId(produtoId).collect {
                it?.let { produto ->
                    binding.apply {
                        mImageView.tentaCarregarImagem(produto.imagem)
                        mTextViewNome.text = produto.nome
                        mTextViewDescricao.text = produto.descricao
                        mTextValor.text = produto.valor.toString()
                    }
                } ?: finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_remover -> {
                lifecycleScope.launch {
                    produtoId.let { produtoDao.deleta(it) }
                    finish()
                }
            }
            R.id.menu_editar -> {
                Intent(this, FormularioProdutoActivity::class.java).apply {
                    putExtra("PRODUTO_ID", produtoId)
                    putExtra("USUARIO_PRODUTO", usuarioProduto)
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}