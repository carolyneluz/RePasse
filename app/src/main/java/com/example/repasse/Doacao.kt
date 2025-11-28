package com.example.repasse

data class Doacao(
    val id: Long? = null,
    val nomeItem: String,
    val categoria: String,
    val quantidade: Int,
    val condicao: String,
    val observacoes: String = "",
    val status: String = "Pendente"
)
