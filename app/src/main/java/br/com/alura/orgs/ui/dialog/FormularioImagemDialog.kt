package br.com.alura.orgs.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import br.com.alura.orgs.databinding.FormularioImagemBinding
import br.com.alura.orgs.extensions.tentaCarregarImagem
import coil.load

class FormularioImagemDialog(val context: Context) {
    fun mostra(urlPadrao: String? = null, quandoImagemCarregada: (imagem: String) -> Unit) {
        FormularioImagemBinding.inflate(LayoutInflater.from(context)).apply {

            urlPadrao?.let {
                formularioImg.tentaCarregarImagem(it)
                textUrl.setText(it)
            }

            btnFormularioImg.setOnClickListener {
                val url = textUrl.text.toString()
                formularioImg.load(url)
            }

            AlertDialog.Builder(context)
                .setView(root)
                .setPositiveButton("Confirmar") { _, _ ->
                    val url = textUrl.text.toString()
                    quandoImagemCarregada(url)
                }
                .setNegativeButton("Cancelar") { alert, _ ->
                    alert.dismiss()
                }.show()
        }
    }
}