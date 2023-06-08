package br.com.alura.orgs.util

import android.content.Context
import android.content.Intent
import android.widget.Toast

fun Context.vaiPara(Class: Class<*>, Intent: Intent.() -> Unit = {}) {
    Intent(this, Class).apply {
        Intent()
        startActivity(this)
    }
}

fun Context.toast(mensagem: String) {
    Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
}