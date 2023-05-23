package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.alura.orgs.R
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityListaProdutosBinding
import br.com.alura.orgs.ui.recycler.adapter.ListaProdutoAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.*

class ListaProdutosActivity : AppCompatActivity(), View.OnClickListener {

    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }
    private val adapter by lazy {
        ListaProdutoAdapter(this@ListaProdutosActivity)
    }

    private val produtoDao by lazy {
        AppDatabase.instanciaDB(this).produtoDao()
    }

    private lateinit var fabAdd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            repeat(1000) {
                Log.i("Teste Job coritine", "onResume: $it")
                delay(1000L)
            }
        }

        lifecycleScope.launch {
            val produtos = produtoDao.buscaTodos()
            adapter.atualiza(produtos)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            fabAdd -> {
                vaiParaFormularioProduto()
            }
        }
    }

    private fun configuraFab() {
        fabAdd = binding.btnAdd
        fabAdd.setOnClickListener(this)
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.mRecycler
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this@ListaProdutosActivity)
    }

    private fun vaiParaFormularioProduto() {
        val intent = Intent(this@ListaProdutosActivity, FormularioProdutoActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        lifecycleScope.launch {
            when (item.itemId) {
                R.id.nomDesc -> {
                    adapter.atualiza(produtoDao.selectNomDesc())
                }
                R.id.nomAsc -> {
                    adapter.atualiza(produtoDao.selectNomAsc())
                }
                R.id.desDes -> {
                    adapter.atualiza(produtoDao.selectDesDesc())
                }
                R.id.desAsc -> {
                    adapter.atualiza(produtoDao.selectDesAsc())
                }
                R.id.valorDesc -> {
                    adapter.atualiza(produtoDao.selectValorDesc())
                }
                R.id.valorAsc -> {
                    adapter.atualiza(produtoDao.selectValorAsc())
                }
                R.id.semOrdenacao -> {
                    adapter.atualiza(produtoDao.buscaTodos())
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}