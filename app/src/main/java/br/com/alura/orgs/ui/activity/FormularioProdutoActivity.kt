package br.com.alura.orgs.ui.activity

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.orgs.R
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityFormularioProdutoBinding
import br.com.alura.orgs.extensions.tentaCarregarImagem
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.ui.dialog.FormularioImagemDialog
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }
    private var url: String? = null
    private var idProduto = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar produto"
        configuraBotaoSalvar()
        binding.activityFormularioProdutoImagem.setOnClickListener {
            FormularioImagemDialog(this@FormularioProdutoActivity).mostra(url) { imagem ->
                url = imagem
                binding.activityFormularioProdutoImagem.tentaCarregarImagem(url)
            }
        }

        intent?.extras?.apply {
            title = "Carregar produto"
            val produto = getSerializable("PRODUTO") as Produto
            idProduto = produto.id
            url = produto.imagem
            binding.apply {
                activityFormularioProdutoImagem.tentaCarregarImagem(produto.imagem)
                descricao.setText(produto.descricao)
                nome.setText(produto.nome)
                valor.setText(produto.valor.toPlainString())
            }
        }
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = findViewById<Button>(R.id.btn_salvar)
        val db = AppDatabase.instanciaDB(this)
        val dao = db.produtoDao()
        botaoSalvar.setOnClickListener {
            val produtoNovo = criaProduto()

            if(idProduto > 0) {
                dao.atualiza(produtoNovo)
                finish()
            } else {
                dao.salva(produtoNovo)
                finish()
            }
        }
    }

    private fun criaProduto(): Produto {
        val campoNome = binding.nome
        val nome = campoNome.text.toString()
        val campoDescricao = binding.descricao
        val descricao = campoDescricao.text.toString()
        val campoValor = binding.valor
        val valorEmTexto = campoValor.text.toString()

        val valor =
            if (valorEmTexto.isBlank()) BigDecimal.ZERO else BigDecimal(valorEmTexto)

        return Produto(
            idProduto,
            nome,
            descricao,
            valor,
            url
        )
    }

}