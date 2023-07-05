package br.com.alura.orgs.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.alura.orgs.R
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityListaProdutosBinding
import br.com.alura.orgs.ui.recycler.adapter.ListaProdutoUsuarioAdapter
import br.com.alura.orgs.util.vaiPara
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class ListaProdutosActivity : AppBaseActivity(), View.OnClickListener {

    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }
    private val adapter by lazy {
        ListaProdutoUsuarioAdapter(this@ListaProdutosActivity)
    }
    private val produtoDao by lazy {
        AppDatabase.instanciaDB(this).produtoDao()
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
                usuario.filterNotNull().collect { usuario ->
                    buscaProdutosUsuario(usuario.id)
                }
            }
        }

        binding.btnAdd.setOnClickListener(this)
    }

    private suspend fun buscaProdutosUsuario(idUsuario: Int) {
        produtoDao.buscaTodosUsuario(idUsuario).collect {
            adapter.atualiza(it)
        }
    }

    override fun onClick(v: View?) {
        binding.apply {
            when (v) {
                btnAdd -> {
                    vaiPara(FormularioProdutoActivity::class.java)
                }
            }
        }
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.mRecycler
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this@ListaProdutosActivity)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        lifecycleScope.launch {
            when (item.itemId) {
                R.id.todosProdutos -> {
                    vaiPara(ListaTodosProdutosActivity::class.java)
                }
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
//                R.id.sair -> {
//                    deslogaUsuario()
//                }
                R.id.perfil -> {
                    vaiPara(PerfilActivity::class.java)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}