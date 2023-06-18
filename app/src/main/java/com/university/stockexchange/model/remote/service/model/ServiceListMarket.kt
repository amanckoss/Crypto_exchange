package com.university.stockexchange.model.remote.service

data class ServiceListMarket(
        val status: Boolean,
        val order_book_buy : List<OrderBookStock>,
        val order_book_sell : List<OrderBookStock>
)

data class OrderBookStock(val name: String, val id: Int, val  amount:Int, val operation:String, val price:String)

data class UserWallet(val status: Boolean, val money: Float, val invested_money: Float, val wallet: List<WalletAction>)

data class MyOrdersData(val status: Boolean, val invested_stock: List<WalletAction>)

data class WalletAction(val id: Int, val name: String, val amount: Int, val purchase_price: Float)

data class AccountData(val email: String)

data class ServiceOrderSell(val status: Boolean, val data: List<SellOrderData>)

data class SellOrderData(val id: Int, val amount: Int, val price: Float)