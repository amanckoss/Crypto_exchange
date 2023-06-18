package com.university.stockexchange.main_ui.my_order

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.university.stockexchange.R
import com.university.stockexchange.ServiceApp
import com.university.stockexchange.main_ui.add_order.AddOrderActivity
import com.university.stockexchange.main_ui.market.ActionItem
import com.university.stockexchange.main_ui.wallet.WalletAdapter
import com.university.stockexchange.main_ui.wallet.WalletStockItem
import com.university.stockexchange.model.remote.service.MyOrdersData
import com.university.stockexchange.model.remote.service.OrderBookStock
import com.university.stockexchange.model.remote.service.WalletAction
import com.university.stockexchange.orders_ui.CloseOrderActivity
import kotlinx.android.synthetic.main.fragment_market.*
import kotlinx.android.synthetic.main.fragment_my_orders.*
import kotlinx.android.synthetic.main.fragment_wallet.*

class MyOrders : Fragment() {

    private var adapter: WalletAdapter? = null
    private lateinit var passwordViewModel: MyOrdersViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_my_orders, container, false)
        passwordViewModel = ViewModelProvider(this).get(MyOrdersViewModel::class.java)
        val prefs = root.context.getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
        passwordViewModel.fetchQuestList((activity?.application as? ServiceApp)?.serviceApi, prefs.getString("api_token", "-1")!!)

        passwordViewModel.getListOrders()?.observe(viewLifecycleOwner) {
            it?.let {
                rvMyOrders.hasFixedSize()
                rvMyOrders.layoutManager = LinearLayoutManager(root.context)
                adapter = WalletAdapter(fillArray(it.invested_stock), root.context)
                rvMyOrders.adapter = adapter
                adapter!!.onItemClick = { contact ->
                    val popupMenu = PopupMenu(context, adapter!!.clickedView)
                    popupMenu.menuInflater.inflate(R.menu.my_orders_menu, popupMenu.menu)
                    popupMenu.setOnMenuItemClickListener { item ->
                        passwordViewModel.cancelOrder((activity?.application as? ServiceApp)?.serviceApi,
                            prefs.getString("api_token", "-1")!!,
                        contact.id!!)
                        true
                    }
                    popupMenu.show()
                }
            }
        }
        return root
    }

    private fun fillArray(wallet: List<WalletAction>):ArrayList<WalletStockItem> {
        val listItemArray = ArrayList<WalletStockItem>()
        for (stock in wallet) {
            val listItem = WalletStockItem(stock.name, stock.amount, stock.purchase_price, stock.id)
            listItemArray.add(listItem)
        }
        return listItemArray
    }
}