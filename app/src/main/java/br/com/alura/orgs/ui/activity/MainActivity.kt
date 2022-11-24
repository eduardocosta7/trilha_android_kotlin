package br.com.alura.orgs.ui.activity

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.orgs.R
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.ui.recycler.adapter.ListaProdutoAdapter
import java.math.BigDecimal

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.mRecycler)
        recyclerView.adapter = ListaProdutoAdapter(
            this@MainActivity, listOf(
                Produto(
                    "teste",
                    "teste descricao",
                    BigDecimal("19.99"),
                ),
                Produto(
                    "teste 1",
                    "teste descricao 1",
                    BigDecimal("29.99"),
                ),
                Produto(
                    "teste 2",
                    "teste descricao 2",
                    BigDecimal("59.99"),
                )
            )
        )
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
    }
}