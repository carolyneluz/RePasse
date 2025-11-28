package com.example.repasse

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListaDoacoesActivity : AppCompatActivity() {

    private lateinit var dbHelper: DoacaoDbHelper
    private lateinit var adapter: DoacaoAdapter

    private lateinit var tvSaudacao: TextView
    private lateinit var rvDoacoes: RecyclerView
    private lateinit var btnNovaDoacao: Button

    private val cadastroLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            carregarDoacoes()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_doacoes)

        dbHelper = DoacaoDbHelper(this)

        tvSaudacao = findViewById(R.id.tvSaudacao)
        rvDoacoes = findViewById(R.id.rvDoacoes)
        btnNovaDoacao = findViewById(R.id.btnNovaDoacao)

        val nomeVoluntario = intent.getStringExtra("NOME_VOLUNTARIO") ?: ""
        tvSaudacao.text = if (nomeVoluntario.isNotBlank()) {
            "Olá, $nomeVoluntario!"
        } else {
            "Olá!"
        }

        adapter = DoacaoAdapter(
            mutableListOf(),
            onItemClick = { doacao -> abrirEdicao(doacao) },
            onItemLongClick = { doacao -> confirmarExclusao(doacao) }
        )

        rvDoacoes.layoutManager = LinearLayoutManager(this)
        rvDoacoes.adapter = adapter

        btnNovaDoacao.setOnClickListener {
            abrirCadastro()
        }

        carregarDoacoes()
    }

    private fun carregarDoacoes() {
        val lista = dbHelper.listarDoacoes()
        adapter.atualizarLista(lista)
    }

    private fun abrirCadastro() {
        val intent = Intent(this, CadastroDoacaoActivity::class.java)
        cadastroLauncher.launch(intent)
    }

    private fun abrirEdicao(doacao: Doacao) {
        val intent = Intent(this, CadastroDoacaoActivity::class.java)
        intent.putExtra("DOACAO_ID", doacao.id ?: -1L)
        cadastroLauncher.launch(intent)
    }

    private fun confirmarExclusao(doacao: Doacao) {
        AlertDialog.Builder(this)
            .setTitle("Remover doação")
            .setMessage("Tem certeza que deseja remover \"${doacao.nomeItem}\"?")
            .setPositiveButton("Remover") { _, _ ->
                doacao.id?.let {
                    dbHelper.deletarDoacao(it)
                    carregarDoacoes()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
