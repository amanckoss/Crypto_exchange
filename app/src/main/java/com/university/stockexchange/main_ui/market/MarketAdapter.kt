package com.university.stockexchange.main_ui.market

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.university.stockexchange.R

class MarketAdapter (listArray:List<ActionItem>, context: Context): RecyclerView.Adapter<MarketAdapter.ViewHolder>() {
    var onItemClick: ((ActionItem) -> Unit)? = null
    var listArrayAdapter = listArray
    var contextR = context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvTitle = view.findViewById<TextView>(R.id.StockItemView)
        val tvSell = view.findViewById<TextView>(R.id.StockSellView)
        val tvBuy = view.findViewById<TextView>(R.id.StockBuyView)

        fun bind(listItem: ActionItem, context: Context) {
            tvTitle.text = listItem.titleText
            tvSell.text = listItem.sellPrice
            tvBuy.text = listItem.buyPrice
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(listArrayAdapter[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(contextR)
        return ViewHolder(inflater.inflate(R.layout.stock_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = listArrayAdapter.get(position)
        holder.bind(listItem, contextR)
    }

    override fun getItemCount(): Int {
        return listArrayAdapter.size
    }
}