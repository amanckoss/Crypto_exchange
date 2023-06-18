package com.university.stockexchange.main_ui.market

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.university.stockexchange.model.remote.service.ServiceApi
import com.university.stockexchange.model.remote.service.ServiceListMarket
import com.university.stockexchange.model.remote.service.model.OrderData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MarketViewModel(application: Application) : AndroidViewModel(application) {

    private val compositeDisposable =  CompositeDisposable()
    var data: MutableLiveData<ServiceListMarket>? = MutableLiveData()
    var orderData: MutableLiveData<OrderData> = MutableLiveData()

    fun fetchQuestList(serviceApi: ServiceApi?, tokenApi: String) {
        serviceApi?.let {
            compositeDisposable.add(serviceApi.getDataMarket(tokenApi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({data ->
                    this.data?.value = data
                    Log.d("f", data.toString())
                }, {throwable ->
                    Log.d("f", throwable.toString())
                }))
        }
    }

    fun fetchOrderQuest(serviceApi: ServiceApi?, stock_name:String, tokenApi: String, operation: String, amount: String, price: String) {
        serviceApi?.let {
            compositeDisposable.add(serviceApi.postCloseOrder(stock_name, tokenApi, operation, amount, price)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({data ->
                        orderData.value = data
                        Log.d("ffff", data.toString())
                    }, {throwable ->
                        Log.d("f", throwable.toString())
                    }))
        }
    }

    fun getOrderDataStatus() = orderData
    fun getPasswordData() = data
}