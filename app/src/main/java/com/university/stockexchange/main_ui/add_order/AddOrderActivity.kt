package com.university.stockexchange.main_ui.add_order

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.university.stockexchange.MainActivity
import com.university.stockexchange.R
import com.university.stockexchange.ServiceApp
import kotlinx.android.synthetic.main.activity_add_order.*
import kotlinx.android.synthetic.main.activity_close_order.*
import kotlinx.android.synthetic.main.activity_close_order.amount_action_input
import kotlinx.android.synthetic.main.activity_close_order.stock_name
import kotlinx.android.synthetic.main.activity_close_order.tv_sell_amount
import kotlinx.android.synthetic.main.activity_close_order.tv_sell_price
import kotlinx.android.synthetic.main.fragment_wallet.*
import kotlin.math.log

class AddOrderActivity : AppCompatActivity() {
    private lateinit var passwordViewModel: AddOrderViewModel
    var apiToken: String ?= null
    var stock_id: Int = 1

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_order)
        passwordViewModel = ViewModelProvider(this).get(AddOrderViewModel::class.java)
        apiToken = intent.getStringExtra("api_token")

        stock_name.text = intent.getStringExtra("name")
        tv_sell_price.text = intent.getStringExtra("sell_price")
        tv_sell_amount.text = intent.getIntExtra("sell_amount", -1).toString()
        passwordViewModel.sellOrderQuery((this.application as? ServiceApp)?.serviceApi, apiToken!!, intent.getStringExtra("name")!!)


        passwordViewModel.getOrderData()?.observe(this) {
            it?.let {
                if (it.status && it.data.isNotEmpty()) {
                    stock_id = it.data[0].id
                    tv_sell_price.text = it.data[0].price.toString() + '$'
                    tv_sell_amount.text = it.data[0].amount.toString()
                }
            }
        }

        passwordViewModel.getCallback().observe(this) {
            it?.let {
                if (it.status) {
                    Toast.makeText(this, "Ордер успішно обробленний", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Toast.makeText(this, it.error, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun onSellClick(view: View) {
        val amount = amount_action_input.text.toString()
        val price = price_action_input.text.toString()
        passwordViewModel.fetchOrderQuest((application as? ServiceApp)?.serviceApi,
            this.stock_id, apiToken!!, amount, price)
    }
}