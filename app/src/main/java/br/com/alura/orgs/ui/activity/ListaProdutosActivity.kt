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
import br.com.alura.orgs.preferences.dataStore
import br.com.alura.orgs.preferences.usuarioLogadoPreferences
import br.com.alura.orgs.ui.recycler.adapter.ListaProdutoAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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

    private val usuarioDao by lazy {
        AppDatabase.instanciaDB(this).usuarioDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        controles()
    }

    private fun controles() {
        lifecycleScope.launch {
            launch {
                produtoDao.buscaTodos().collect {
                    adapter.atualiza(it)
                }
            }

            dataStore.data.collect { preferences ->
                preferences[usuarioLogadoPreferences]?.let { usuarioId ->
                    usuarioDao.buscaPorId(usuarioId).collect {
                        Log.e("usuario", it.toString())
                    }
                }
            }
        }

        binding.btnAdd.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        binding.apply {
            when (v) {
                btnAdd -> {
                    vaiParaFormularioProduto()
                }
            }
        }
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
                    adapter.atualiza(produtoDao.selectSemFiltro())
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}