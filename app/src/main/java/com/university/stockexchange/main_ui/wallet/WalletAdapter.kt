package com.university.stockexchange.main_ui.wallet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.university.stockexchange.R

class WalletAdapter (listArray: List<WalletStockItem>, context: Context): RecyclerView.Adapter<WalletAdapter.ViewHolder>() {
    var onItemClick: ((WalletStockItem) -> Unit)? = null
    var clickedView: View? = null
    var listArrayAdapter = listArray
    var contextR = context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvTitle = view.findViewById<TextView>(R.id.StockItemView)
        val tvAmount = view.findViewById<TextView>(R.id.StockSellView)
        val tvPrice = view.findViewById<TextView>(R.id.StockBuyView)

        fun bind(listItem: WalletStockItem, context: Context) {
            tvTitle.text = listItem.name
            tvAmount.text = listItem.amount.toString()
            tvPrice.text = listItem.purchasePrice.toString()
        }

        init {
            itemView.setOnClickListener {
                clickedView = it
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