package com.university.stockexchange.main_ui.account

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.university.stockexchange.model.remote.service.AccountData
import com.university.stockexchange.model.remote.service.ServiceApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MoreViewModel : ViewModel() {
    private val compositeDisposable =  CompositeDisposable()
    var data: MutableLiveData<AccountData>? = MutableLiveData()

    fun fetchQuestList(serviceApi: ServiceApi?, tokenApi: String) {
        serviceApi?.let {
            compositeDisposable.add(serviceApi.getDataAccount(tokenApi)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({data ->
                        Log.d("ffff", "order")
                        this.data?.value = data
                    }, {throwable ->
                        Log.d("f", throwable.toString())
                    }))
        }
    }

    fun getAccountData() = data
}