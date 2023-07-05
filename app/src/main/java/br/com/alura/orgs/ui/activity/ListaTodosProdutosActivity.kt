package br.com.alura.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityListaTodosProdutosBinding
import br.com.alura.orgs.ui.recycler.adapter.ListaProdutoUsuarioAdapter
import br.com.alura.orgs.ui.recycler.adapter.ListaProdutosAdapter
import kotlinx.coroutines.launch

class ListaTodosProdutosActivity : AppBaseActivity() {

    private val binding by lazy {
        ActivityListaTodosProdutosBinding.inflate(layoutInflater)
    }
    private val adapter by lazy {
        ListaProdutosAdapter(this@ListaTodosProdutosActivity)
    }
    private val produtoDao by lazy {
        AppDatabase.instanciaDB(this).produtoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecycle()
    }

    fun configuraRecycle() {
        val recycleView = binding.mRecycler
        recycleView.adapter = adapter
        recycleView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            buscaTodosProdutos()
        }
    }

    private suspend fun buscaTodosProdutos() {
        produtoDao.buscaTodos().collect {
            adapter.atualiza(it)
        }
    }
}