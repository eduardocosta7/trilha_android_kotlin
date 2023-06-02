package br.com.alura.orgs.util

import android.content.Context
import android.content.Intent

fun Context.vaiPara(Class: Class<*>, Intent: Intent.() -> Unit = {}) {
    Intent(this, Class).apply {
        Intent()
        startActivity(this)
    }
}