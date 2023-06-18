package com.university.stockexchange.main_ui.my_order

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.university.stockexchange.model.remote.service.MyOrdersData
import com.university.stockexchange.model.remote.service.ServiceApi
import com.university.stockexchange.model.remote.service.ServiceListMarket
import com.university.stockexchange.model.remote.service.model.OrderData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MyOrdersViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable =  CompositeDisposable()
    var ordersData: MutableLiveData<MyOrdersData>? = MutableLiveData()

    fun fetchQuestList(serviceApi: ServiceApi?, tokenApi: String) {
        serviceApi?.let {
            compositeDisposable.add(serviceApi.getMyOrders(tokenApi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({data ->
                    this.ordersData?.value = data
                    Log.d("f", data.toString())
                }, {throwable ->
                    Log.d("f", throwable.toString())
                }))
        }
    }

    fun cancelOrder(serviceApi: ServiceApi?, tokenApi: String, stock_id: Int) {
        serviceApi?.let {
            compositeDisposable.add(serviceApi.cancelOrder(tokenApi, stock_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({data ->
                    fetchQuestList(serviceApi, tokenApi)
                    Log.d("f", data.toString())
                }, {throwable ->
                    Log.d("f", throwable.toString())
                }))
        }
    }

    fun getListOrders() = ordersData
}