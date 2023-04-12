package br.com.alura.orgs.ui.recycler.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.orgs.databinding.ProdutoItemBinding
import br.com.alura.orgs.model.Produto
import coil.load
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class ListaProdutoAdapter(
    private val context: Context,
    produtos: List<Produto>,
) : RecyclerView.Adapter<ListaProdutoAdapter.ViewHolder>() {

    private val dataSet = produtos.toMutableList()

    class ViewHolder(private val binding: ProdutoItemBinding) :
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
                imageView.load(produto.imagem)
            }
        }

        private fun formataParaMoedaBrasileira(valor: BigDecimal): String? {
            val formatador = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
            return formatador.format(valor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProdutoItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
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
