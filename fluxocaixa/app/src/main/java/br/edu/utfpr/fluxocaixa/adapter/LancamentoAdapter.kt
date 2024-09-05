package br.edu.utfpr.fluxocaixa.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.edu.utfpr.fluxocaixa.R
import br.edu.utfpr.fluxocaixa.entity.Lancamento

class LancamentoAdapter(private val context: Context, private val dataset: List<Lancamento>) :
    RecyclerView.Adapter<LancamentoAdapter.LancamentoViewHolder>() {

    class LancamentoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItemLancamento: TextView = view.findViewById(R.id.tvItemLancamento)
        val cardItemLancamento: CardView = view.findViewById(R.id.cardItemLancamento)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LancamentoViewHolder {
        val adapterLayout = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return LancamentoViewHolder(adapterLayout)
    }

    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: LancamentoViewHolder, position: Int) {
        val item = dataset[position]
        holder.tvItemLancamento.text = item.toString()
        holder.cardItemLancamento.setCardBackgroundColor(
            if (item.tipo == "Crédito") context.getColor(R.color.credito_claro)
            else context.getColor(R.color.debito_claro)
        )
    }
}