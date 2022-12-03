package br.com.alura.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.orgs.R
import br.com.alura.orgs.dao.ProdutoDao
import br.com.alura.orgs.ui.recycler.adapter.ListaProdutoAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(R.layout.activity_main), View.OnClickListener {

    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        val dao = ProdutoDao()

        val recyclerView = findViewById<RecyclerView>(R.id.mRecycler)
        recyclerView.adapter = ListaProdutoAdapter(this@MainActivity, dao.buscaTodos())
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

        fab = findViewById(R.id.btn_add)
        fab.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            fab -> {
                val intent = Intent(this@MainActivity, FormularioProdutoActivity::class.java)
                startActivity(intent)
            }
        }
    }
}