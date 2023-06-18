package com.university.stockexchange.main_ui.wallet

import android.annotation.SuppressLint
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
import com.university.stockexchange.main_ui.add_order.AddOrderActivity
import com.university.stockexchange.model.remote.service.WalletAction
import com.university.stockexchange.orders_ui.CloseOrderActivity
import kotlinx.android.synthetic.main.fragment_wallet.*

class WalletFragment : Fragment() {
  private var adapter: WalletAdapter? = null
  private lateinit var walletViewModel: WalletViewModel

  @SuppressLint("SetTextI18n")
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    walletViewModel = ViewModelProvider(this).get(WalletViewModel::class.java)
    val root = inflater.inflate(R.layout.fragment_wallet, container, false)
    val prefs = root.context.getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
    walletViewModel.fetchQuestList((activity?.application as? ServiceApp)?.serviceApi, prefs.getString("api_token", "-1")!!)

    walletViewModel.getWalletData()?.observe(viewLifecycleOwner) {
      it?.let {
        all_money.text = String.format(resources.getString(R.string.all_money), it.money)
        invested_money.text = it.invested_money.toString() + '$'
        free_money.text = (it.money - it.invested_money).toString()  + '$'
        rvWallet.hasFixedSize()
        rvWallet.layoutManager = LinearLayoutManager(root.context)
        adapter = WalletAdapter(fillArray(it.wallet), root.context)
        rvWallet.adapter = adapter
        adapter!!.onItemClick = { contact ->
          startActivity(
            Intent(context, AddOrderActivity::class.java)
              .putExtra("api_token", prefs.getString("api_token", "-1"))
              .putExtra("name", contact.name)
          )
        }
      }
    }
    return root
  }

  private fun fillArray(wallet: List<WalletAction>):ArrayList<WalletStockItem> {
    val listItemArray = ArrayList<WalletStockItem>()
    for (stock in wallet) {
      val listItem = WalletStockItem(stock.name, stock.amount, stock.purchase_price, 0)
      listItemArray.add(listItem)
    }
    return listItemArray
  }
}