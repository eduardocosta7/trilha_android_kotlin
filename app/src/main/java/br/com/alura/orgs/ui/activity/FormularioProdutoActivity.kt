package br.com.alura.orgs.ui.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityFormularioProdutoBinding
import br.com.alura.orgs.extensions.tentaCarregarImagem
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.ui.dialog.FormularioImagemDialog
import kotlinx.coroutines.launch
import java.math.BigDecimal

class FormularioProdutoActivity : AppBaseActivity() {

    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }

    private val produtoDao by lazy {
        AppDatabase.instanciaDB(this).produtoDao()
    }

    private var url: String? = null
    private var produtoId = 0
    private var usuarioProduto = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        controles()
    }

    private fun controles() {
        title = "Cadastrar produto"
        configuraBotaoSalvar()
        binding.activityFormularioProdutoImagem.setOnClickListener {
            FormularioImagemDialog(this@FormularioProdutoActivity).mostra(url) { imagem ->
                url = imagem
                binding.activityFormularioProdutoImagem.tentaCarregarImagem(url)
            }
        }

        intent?.extras?.apply {
            produtoId = getInt("PRODUTO_ID", 0)
            usuarioProduto = getBoolean("USUARIO_PRODUTO", false)
        }

        if (usuarioProduto)
            binding.usuario.visibility = View.VISIBLE
        else
            binding.usuario.visibility = View.GONE

        lifecycleScope.launch {
            produtoDao.buscaPorId(produtoId).collect {
                it?.let { preencheCampos(it) }
            }
        }
    }

    private fun preencheCampos(produto: Produto) {
        title = "Alterar produto"
        url = produto.imagem
        binding.apply {
            usuario.setText(produto.idUsuario.toString())
            activityFormularioProdutoImagem.tentaCarregarImagem(produto.imagem)
            descricao.setText(produto.descricao)
            nome.setText(produto.nome)
            valor.setText(produto.valor.toPlainString())
        }
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.btnSalvar
        botaoSalvar.setOnClickListener {
            lifecycleScope.launch {
                usuario.value?.let {
                    val id = if (usuarioProduto) binding.usuario.text.toString().toInt() else it.id
                    val produtoNovo = criaProduto(id)
                    produtoDao.salva(produtoNovo)
                    finish()
                }
            }
        }
    }

    private fun criaProduto(idUsuario: Int): Produto {
        val campoNome = binding.nome
        val nome = campoNome.text.toString()
        val campoDescricao = binding.descricao
        val descricao = campoDescricao.text.toString()
        val campoValor = binding.valor
        val valorEmTexto = campoValor.text.toString()

        val valor =
            if (valorEmTexto.isBlank()) BigDecimal.ZERO else BigDecimal(valorEmTexto)

        return Produto(
            produtoId,
            nome,
            descricao,
            valor,
            url,
            idUsuario
        )
    }

}