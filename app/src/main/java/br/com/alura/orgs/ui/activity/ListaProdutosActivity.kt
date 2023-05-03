package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityListaProdutosBinding
import br.com.alura.orgs.ui.recycler.adapter.ListaProdutoAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListaProdutosActivity : AppCompatActivity(), View.OnClickListener {

    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }
    private val adapter by lazy {
        ListaProdutoAdapter(this@ListaProdutosActivity)
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
        val db = AppDatabase.instanciaDB(this)
        val produtoDao = db.produtoDao()
        adapter.atualiza(produtoDao.buscaTodos())
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
}