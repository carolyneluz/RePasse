package com.example.repasse

import android.app.Activity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CadastroDoacaoActivity : AppCompatActivity() {

    private lateinit var dbHelper: DoacaoDbHelper

    private lateinit var etNomeItem: EditText
    private lateinit var etQuantidade: EditText
    private lateinit var spCategoria: Spinner
    private lateinit var spCondicao: Spinner
    private lateinit var etObservacoes: EditText
    private lateinit var btnSalvar: Button

    private var doacaoId: Long? = null
    private var doacaoAtual: Doacao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_doacao)

        dbHelper = DoacaoDbHelper(this)

        etNomeItem = findViewById(R.id.etNomeItem)
        etQuantidade = findViewById(R.id.etQuantidade)
        spCategoria = findViewById(R.id.spCategoria)
        spCondicao = findViewById(R.id.spCondicao)
        etObservacoes = findViewById(R.id.etObservacoes)
        btnSalvar = findViewById(R.id.btnSalvar)

        configurarSpinners()

        val idRecebido = intent.getLongExtra("DOACAO_ID", -1L)
        if (idRecebido != -1L) {
            doacaoId = idRecebido
            carregarDoacao()
        }

        btnSalvar.setOnClickListener {
            salvarOuAtualizar()
        }
    }

    private fun configurarSpinners() {
        val categorias = resources.getStringArray(R.array.categorias_doacao)
        val adapterCategorias = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            categorias
        )
        adapterCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spCategoria.adapter = adapterCategorias

        val condicoes = resources.getStringArray(R.array.condicoes_doacao)
        val adapterCondicoes = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            condicoes
        )
        adapterCondicoes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spCondicao.adapter = adapterCondicoes
    }

    private fun carregarDoacao() {
        val id = doacaoId ?: return
        val doacao = dbHelper.obterDoacaoPorId(id) ?: return
        doacaoAtual = doacao

        etNomeItem.setText(doacao.nomeItem)
        etQuantidade.setText(doacao.quantidade.toString())
        etObservacoes.setText(doacao.observacoes)

        selecionarItemSpinner(spCategoria, doacao.categoria)
        selecionarItemSpinner(spCondicao, doacao.condicao)
    }

    private fun selecionarItemSpinner(spinner: Spinner, valor: String) {
        val adapter = spinner.adapter
        for (i in 0 until adapter.count) {
            if (adapter.getItem(i).toString().equals(valor, ignoreCase = true)) {
                spinner.setSelection(i)
                break
            }
        }
    }

    private fun salvarOuAtualizar() {
        val nome = etNomeItem.text.toString().trim()
        val quantidadeTexto = etQuantidade.text.toString().trim()
        val categoria = spCategoria.selectedItem?.toString() ?: ""
        val condicao = spCondicao.selectedItem?.toString() ?: ""
        val observacoesTexto = etObservacoes.text.toString().trim()

        if (nome.isEmpty() || quantidadeTexto.isEmpty()) {
            Toast.makeText(this, "Preencha pelo menos nome e quantidade", Toast.LENGTH_SHORT).show()
            return
        }

        val quantidade = quantidadeTexto.toIntOrNull()
        if (quantidade == null || quantidade <= 0) {
            Toast.makeText(this, "Quantidade inválida", Toast.LENGTH_SHORT).show()
            return
        }

        val status = doacaoAtual?.status ?: "Pendente"

        val doacao = Doacao(
            id = doacaoId,
            nomeItem = nome,
            categoria = categoria,
            quantidade = quantidade,
            condicao = condicao,
            observacoes = observacoesTexto,
            status = status
        )

        if (doacaoId == null) {
            dbHelper.inserirDoacao(doacao)
            Toast.makeText(this, "Doação cadastrada com sucesso!", Toast.LENGTH_SHORT).show()
        } else {
            dbHelper.atualizarDoacao(doacao)
            Toast.makeText(this, "Doação atualizada com sucesso!", Toast.LENGTH_SHORT).show()
        }

        setResult(Activity.RESULT_OK)
        finish()
    }
}
