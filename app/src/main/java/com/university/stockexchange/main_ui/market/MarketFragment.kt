package com.university.stockexchange.main_ui.market

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.university.stockexchange.R
import com.university.stockexchange.ServiceApp
import com.university.stockexchange.model.remote.service.OrderBookStock
import com.university.stockexchange.orders_ui.CloseOrderActivity
import kotlinx.android.synthetic.main.fragment_market.*

class MarketFragment : Fragment() {

  private var adapter: MarketAdapter? = null
  private lateinit var passwordViewModel: MarketViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val root = inflater.inflate(R.layout.fragment_market, container, false)
    passwordViewModel = ViewModelProvider(this).get(MarketViewModel::class.java)
    val prefs = root.context.getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
    passwordViewModel.fetchQuestList((activity?.application as? ServiceApp)?.serviceApi, prefs.getString("api_token", "-1")!!)

      passwordViewModel.getPasswordData()?.observe(viewLifecycleOwner) {
        it?.let {
          rvMarket.hasFixedSize()
          rvMarket.layoutManager = LinearLayoutManager(root.context)
          adapter = MarketAdapter(fillArray(it.order_book_buy, it.order_book_sell), root.context)
          rvMarket.adapter = adapter
          adapter!!.onItemClick = { contact ->
            startActivity(
              Intent(context, CloseOrderActivity::class.java)
                .putExtra("api_token", prefs.getString("api_token", "-1"))
                .putExtra("name", contact.titleText)
                .putExtra("sell_price", contact.sellPrice)
                .putExtra("sell_amount", contact.sellAmount)
                .putExtra("buy_price", contact.buyPrice)
                .putExtra("buy_amount", contact.buyAmount)
                .putExtra("stock_id", contact.stockId)
            )
          }
        }
      }
    return root
  }

  private fun fillArray(buyStock: List<OrderBookStock>, sellStock: List<OrderBookStock>):ArrayList<ActionItem> {
    var listItemArray = ArrayList<ActionItem>()
    for (bs in buyStock) {
      var listItem : ActionItem?= null
      for (ss in sellStock) {
        if (ss.name == bs.name) {
          listItem = ActionItem(bs.name, bs.id, ss.price, bs.price, ss.amount, bs.amount)
        }
      }
      if (listItem != null) listItemArray.add(listItem)
    }
    listItemArray = listItemArray.drop(1) as ArrayList<ActionItem>
    return listItemArray
  }
}