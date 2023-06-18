package com.university.stockexchange.main_ui.add_order

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.university.stockexchange.model.remote.service.SellOrderData
import com.university.stockexchange.model.remote.service.ServiceApi
import com.university.stockexchange.model.remote.service.ServiceOrderSell
import com.university.stockexchange.model.remote.service.model.OrderData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AddOrderViewModel: ViewModel() {
    private val compositeDisposable =  CompositeDisposable()
    var data: MutableLiveData<ServiceOrderSell>? = MutableLiveData()
    var order_callback: MutableLiveData<OrderData> = MutableLiveData()

    fun sellOrderQuery(serviceApi: ServiceApi?, tokenApi: String, stock_name: String) {
        serviceApi?.let {
            compositeDisposable.add(serviceApi.getDataOrderSell(tokenApi, stock_name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({data ->
                    this.data?.value = data
                }, {throwable ->
                    Log.d("f", throwable.toString())
                }))
        }
    }

    fun fetchOrderQuest(serviceApi: ServiceApi?, stock_id:Int, tokenApi: String, amount: String, price: String) {
        serviceApi?.let {
            compositeDisposable.add(serviceApi.postSellOrder(tokenApi, stock_id, amount, price)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({data ->
                    order_callback.value = data
                    Log.d("ffff", data.toString())
                }, {throwable ->
                    Log.d("f", throwable.toString())
                }))
        }
    }

    fun getCallback() = order_callback
    fun getOrderData() = data
}