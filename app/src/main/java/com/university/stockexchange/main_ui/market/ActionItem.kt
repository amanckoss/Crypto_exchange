package com.university.stockexchange.main_ui.market

data class ActionItem (
    var titleText:String,
    var stockId: Int,
    var sellPrice:String,
    var buyPrice:String,
    var sellAmount: Int,
    var buyAmount: Int
        )