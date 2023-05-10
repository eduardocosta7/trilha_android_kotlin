package br.com.alura.orgs.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.R
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityDetalheBinding
import br.com.alura.orgs.extensions.tentaCarregarImagem
import br.com.alura.orgs.model.Produto

class DetalheActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDetalheBinding.inflate(layoutInflater)
    }

    private lateinit var produto: Produto

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
        if (::produto.isInitialized) {
            val db = AppDatabase.instanciaDB(this)
            val produtoDao = db.produtoDao()
            when (item.itemId) {
                R.id.menu_remover -> {
                    produtoDao.deleta(produto)
                    finish()
                }
                R.id.menu_editar -> {
                    Intent(this, FormularioProdutoActivity::class.java).apply {
                        putExtra("PRODUTO", produto)
                        startActivity(this)
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("SetTextI18n")
    private fun controles() {
        binding.apply {
            intent?.extras?.apply {
                    produto = getSerializable("PRODUTO") as Produto
                    mImageView.tentaCarregarImagem(produto.imagem)
                    mTextViewNome.text = produto.nome
                    mTextViewDescricao.text = produto.descricao
                    mTextValor.text = produto.valor.toString()
                }
        }
    }
}