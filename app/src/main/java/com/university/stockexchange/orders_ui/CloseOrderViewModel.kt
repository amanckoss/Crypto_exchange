package com.university.stockexchange.orders_ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.university.stockexchange.model.remote.service.ServiceApi
import com.university.stockexchange.model.remote.service.ServiceListMarket
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CloseOrderViewModel: ViewModel() {
    private val compositeDisposable =  CompositeDisposable()
    var data: MutableLiveData<ServiceListMarket>? = MutableLiveData()

    fun fetchQuestList(serviceApi: ServiceApi?, tokenApi: String) {
        serviceApi?.let {
            compositeDisposable.add(serviceApi.getDataMarket(tokenApi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({data ->
                    this.data?.value = data
                }, {throwable ->
                    Log.d("f", throwable.toString())
                }))
        }
    }

    fun getOrderCalback() = data
}