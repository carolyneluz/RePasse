package com.example.repasse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DoacaoAdapter(
    private val doacoes: MutableList<Doacao>,
    private val onItemClick: (Doacao) -> Unit,
    private val onItemLongClick: (Doacao) -> Unit
) : RecyclerView.Adapter<DoacaoAdapter.DoacaoViewHolder>() {

    inner class DoacaoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNomeItem: TextView = itemView.findViewById(R.id.tvNomeItem)
        val tvCategoriaQuantidade: TextView = itemView.findViewById(R.id.tvCategoriaQuantidade)
        val tvCondicao: TextView = itemView.findViewById(R.id.tvCondicao)
        val tvObservacoes: TextView = itemView.findViewById(R.id.tvObservacoes)

        fun bind(doacao: Doacao) {
            tvNomeItem.text = doacao.nomeItem
            tvCategoriaQuantidade.text = "${doacao.categoria} • ${doacao.quantidade} un."
            tvCondicao.text = "Condição: ${doacao.condicao}"

            // aqui entra o texto REAL das observações
            tvObservacoes.text =
                if (doacao.observacoes.isBlank()) "Sem observações"
                else doacao.observacoes

            itemView.setOnClickListener {
                onItemClick(doacao)
            }

            itemView.setOnLongClickListener {
                onItemLongClick(doacao)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoacaoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_doacao, parent, false)
        return DoacaoViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoacaoViewHolder, position: Int) {
        holder.bind(doacoes[position])
    }

    override fun getItemCount(): Int = doacoes.size

    fun atualizarLista(novaLista: List<Doacao>) {
        doacoes.clear()
        doacoes.addAll(novaLista)
        notifyDataSetChanged()
    }
}