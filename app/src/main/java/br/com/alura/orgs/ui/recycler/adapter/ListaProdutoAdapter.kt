package br.com.alura.orgs.ui.recycler.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.orgs.databinding.ProdutoItemBinding
import br.com.alura.orgs.extensions.tentaCarregarImagem
import br.com.alura.orgs.model.Produto
import br.com.alura.orgs.ui.activity.DetalheActivity
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class ListaProdutoAdapter(
    private val context: Context,
    produtos: List<Produto>,
) : RecyclerView.Adapter<ListaProdutoAdapter.ViewHolder>() {

    private val dataSet = produtos.toMutableList()

    class ViewHolder(private val binding: ProdutoItemBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun vincula(produto: Produto) {
            binding.apply {
                val nome = nome
                nome.text = produto.nome
                val descricao = descricao
                descricao.text = produto.descricao
                val valor = valor
                val valorEmMoeda = formataParaMoedaBrasileira(produto.valor)
                valor.text = valorEmMoeda
                imageView.visibility = if (produto.imagem != null) View.VISIBLE else View.GONE
                imageView.tentaCarregarImagem(produto.imagem)

                mCardView.setOnClickListener {
                    onClick(produto)
                }
            }
        }

        private fun onClick(produto: Produto) {
            val intent = Intent(context, DetalheActivity::class.java).apply {
                action = Intent.ACTION_SEND
                putExtra("nome", produto.nome)
                putExtra("descricao", produto.descricao)
                putExtra("valor", formataParaMoedaBrasileira(produto.valor))
                putExtra("imagem", produto.imagem)
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
        val produto = dataSet[position]
        holder.vincula(produto)
    }

    override fun getItemCount(): Int = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun atualiza(produtos: List<Produto>) {
        this.dataSet.clear()
        this.dataSet.addAll(produtos)
        notifyDataSetChanged()
    }

}
