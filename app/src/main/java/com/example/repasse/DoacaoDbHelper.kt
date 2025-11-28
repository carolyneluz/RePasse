package com.example.repasse

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DoacaoDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val sql = """
            CREATE TABLE $TABLE_DOACOES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOME TEXT NOT NULL,
                $COLUMN_CATEGORIA TEXT NOT NULL,
                $COLUMN_QUANTIDADE INTEGER NOT NULL,
                $COLUMN_CONDICAO TEXT NOT NULL,
                $COLUMN_OBSERVACOES TEXT,
                $COLUMN_STATUS TEXT NOT NULL
            )
        """.trimIndent()

        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_DOACOES")
        if (db != null) onCreate(db)
    }

    // CREATE
    fun inserirDoacao(doacao: Doacao): Long {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put(COLUMN_NOME, doacao.nomeItem)
            put(COLUMN_CATEGORIA, doacao.categoria)
            put(COLUMN_QUANTIDADE, doacao.quantidade)
            put(COLUMN_CONDICAO, doacao.condicao)
            put(COLUMN_OBSERVACOES, doacao.observacoes)
            put(COLUMN_STATUS, doacao.status)
        }
        return db.insert(TABLE_DOACOES, null, valores)
    }

    // READ - lista tudo
    fun listarDoacoes(): MutableList<Doacao> {
        val lista = mutableListOf<Doacao>()
        val db = readableDatabase

        val cursor = db.query(
            TABLE_DOACOES,
            null,
            null,
            null,
            null,
            null,
            "$COLUMN_ID DESC"
        )

        cursor.use {
            while (it.moveToNext()) {
                val id = it.getLong(it.getColumnIndexOrThrow(COLUMN_ID))
                val nome = it.getString(it.getColumnIndexOrThrow(COLUMN_NOME))
                val categoria = it.getString(it.getColumnIndexOrThrow(COLUMN_CATEGORIA))
                val quantidade = it.getInt(it.getColumnIndexOrThrow(COLUMN_QUANTIDADE))
                val condicao = it.getString(it.getColumnIndexOrThrow(COLUMN_CONDICAO))
                val obs = it.getString(it.getColumnIndexOrThrow(COLUMN_OBSERVACOES)) ?: ""
                val status = it.getString(it.getColumnIndexOrThrow(COLUMN_STATUS))

                lista.add(
                    Doacao(
                        id = id,
                        nomeItem = nome,
                        categoria = categoria,
                        quantidade = quantidade,
                        condicao = condicao,
                        observacoes = obs,
                        status = status
                    )
                )
            }
        }
        return lista
    }

    // READ - por ID
    fun obterDoacaoPorId(id: Long): Doacao? {
        val db = readableDatabase

        val cursor = db.query(
            TABLE_DOACOES,
            null,
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        cursor.use {
            if (it.moveToFirst()) {
                val nome = it.getString(it.getColumnIndexOrThrow(COLUMN_NOME))
                val categoria = it.getString(it.getColumnIndexOrThrow(COLUMN_CATEGORIA))
                val qtd = it.getInt(it.getColumnIndexOrThrow(COLUMN_QUANTIDADE))
                val cond = it.getString(it.getColumnIndexOrThrow(COLUMN_CONDICAO))
                val obs = it.getString(it.getColumnIndexOrThrow(COLUMN_OBSERVACOES)) ?: ""
                val status = it.getString(it.getColumnIndexOrThrow(COLUMN_STATUS))

                return Doacao(
                    id = id,
                    nomeItem = nome,
                    categoria = categoria,
                    quantidade = qtd,
                    condicao = cond,
                    observacoes = obs,
                    status = status
                )
            }
        }
        return null
    }

    // UPDATE
    fun atualizarDoacao(doacao: Doacao): Int {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put(COLUMN_NOME, doacao.nomeItem)
            put(COLUMN_CATEGORIA, doacao.categoria)
            put(COLUMN_QUANTIDADE, doacao.quantidade)
            put(COLUMN_CONDICAO, doacao.condicao)
            put(COLUMN_OBSERVACOES, doacao.observacoes)
            put(COLUMN_STATUS, doacao.status)
        }

        return db.update(
            TABLE_DOACOES,
            valores,
            "$COLUMN_ID = ?",
            arrayOf(doacao.id.toString())
        )
    }

    // DELETE
    fun deletarDoacao(id: Long): Int {
        val db = writableDatabase
        return db.delete(
            TABLE_DOACOES,
            "$COLUMN_ID = ?",
            arrayOf(id.toString())
        )
    }

    companion object {
        private const val DATABASE_NAME = "doacoes.db"
        private const val DATABASE_VERSION = 2

        const val TABLE_DOACOES = "doacoes"
        const val COLUMN_ID = "id"
        const val COLUMN_NOME = "nome"
        const val COLUMN_CATEGORIA = "categoria"
        const val COLUMN_QUANTIDADE = "quantidade"
        const val COLUMN_CONDICAO = "condicao"
        const val COLUMN_OBSERVACOES = "observacoes"
        const val COLUMN_STATUS = "status"
    }
}
