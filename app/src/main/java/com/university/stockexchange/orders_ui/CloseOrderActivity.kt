package com.university.stockexchange.orders_ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.university.stockexchange.MainActivity
import com.university.stockexchange.R
import com.university.stockexchange.ServiceApp
import com.university.stockexchange.main_ui.market.MarketViewModel
import kotlinx.android.synthetic.main.activity_close_order.*

class CloseOrderActivity : AppCompatActivity() {
    private lateinit var passwordViewModel: MarketViewModel
    var apiToken: String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_close_order)
        passwordViewModel = ViewModelProvider(this).get(MarketViewModel::class.java)
        apiToken = intent.getStringExtra("api_token")

        stock_name.text = intent.getStringExtra("name")
        tv_sell_price.text = intent.getStringExtra("sell_price")
        tv_sell_amount.text = intent.getIntExtra("sell_amount", -1).toString()
        tv_buy_price.text = intent.getStringExtra("buy_price")
        tv_buy_amount.text = intent.getIntExtra("buy_amount", -1).toString()

        passwordViewModel.getOrderDataStatus().observe(this) {
            it?.let {
                if (it.status) {
                    Toast.makeText(this, "Ордер успішно обробленний", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Toast.makeText(this, "Ордер не обробленний", Toast.LENGTH_LONG).show()
                    amount_action_layout.error = it.error
                }
            }
        }
    }

    fun onBuyClick(view: View) {
        Log.d("ffff", "clicked")
        val amount = amount_action_input.text.toString()
        passwordViewModel.fetchOrderQuest((application as? ServiceApp)?.serviceApi,
                intent.getIntExtra("stock_id", 1).toString(), apiToken!!, "buy", amount, tv_buy_price.text.toString())
    }

    fun onSellClick(view: View) {
        Log.d("ffff", "clicked ${intent.getIntExtra("stock_id", 1)}")
        val amount = amount_action_input.text.toString()
        passwordViewModel.fetchOrderQuest((application as? ServiceApp)?.serviceApi,
                intent.getIntExtra("stock_id", 1).toString(), apiToken!!, "sell", amount, tv_sell_price.text.toString())
    }
}