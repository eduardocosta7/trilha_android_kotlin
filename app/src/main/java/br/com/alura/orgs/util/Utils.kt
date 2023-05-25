package br.com.alura.orgs.util

import android.content.Context
import android.content.Intent

fun Context.vaiPara(Class: Class<*>){
    Intent(this, Class).apply {
        startActivity(this)
    }
}