package com.example.repasse

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NOME_VOLUNTARIO = "NOME_VOLUNTARIO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etNomeUsuario = findViewById<EditText>(R.id.etNomeUsuario)
        val etSenha = findViewById<EditText>(R.id.etSenha)
        val btnEntrar = findViewById<Button>(R.id.btnEntrar)

        btnEntrar.setOnClickListener {
            val nome = etNomeUsuario.text.toString().trim()
            val senha = etSenha.text.toString().trim()

            if (nome.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha nome e senha", Toast.LENGTH_SHORT).show()
            } else {
                // Login bem simples só pra liberar o app
                val intent = Intent(this, ListaDoacoesActivity::class.java)
                intent.putExtra(EXTRA_NOME_VOLUNTARIO, nome)
                startActivity(intent)
            }
        }
    }
}
