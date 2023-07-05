package br.com.alura.orgs.ui.recycler.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.orgs.R
import br.com.alura.orgs.databinding.ProdutoItemBinding
import br.com.alura.orgs.extensions.tentaCarregarImagem
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.ui.activity.DetalheProdutoActivity
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class ListaProdutosAdapter(
    private val context: Context,
    produtos: List<Produto> = emptyList(),
) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

    private val dataSet = produtos.toMutableList()

    class ViewHolder(private val binding: ProdutoItemBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun vincula(produto: Produto, mesmoUsuario: Boolean) {
            binding.apply {
                if (mesmoUsuario)
                    title.visibility = View.VISIBLE

                val valorEmMoeda = formataParaMoedaBrasileira(produto.valor)

                title.text = if (produto.idUsuario != 0) produto.idUsuario.toString() else "Sem usuário"
                nome.text = produto.nome
                descricao.text = produto.descricao
                valor.text = valorEmMoeda
                imageView.visibility = if (produto.imagem != null) View.VISIBLE else View.GONE
                imageView.tentaCarregarImagem(produto.imagem)

                mCardView.setOnClickListener {
                    onClick(produto)
                }
            }
        }

        private fun onClick(produto: Produto) {
            val intent = Intent(context, DetalheProdutoActivity::class.java).apply {
                action = Intent.ACTION_SEND
                putExtra("PRODUTO_ID", produto.id)
                putExtra("USUARIO_PRODUTO", true)
            }

            startActivity(context, intent, null)
        }

        private fun formataParaMoedaBrasileira(valor: BigDecimal): String? {
            val formatador = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
            return formatador.format(valor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProdutoItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produtoAnterior = if(position > 0) dataSet[position - 1] else dataSet[position]
        val produto = dataSet[position]
        val valida = position == 0 || produto.idUsuario == 0 || produtoAnterior.id != produto.id && produtoAnterior.idUsuario != produto.idUsuario
        holder.vincula(produto, valida)
    }

    override fun getItemCount(): Int = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun atualiza(produtos: List<Produto>) {
        this.dataSet.clear()
        this.dataSet.addAll(produtos)
        notifyDataSetChanged()
    }

}
