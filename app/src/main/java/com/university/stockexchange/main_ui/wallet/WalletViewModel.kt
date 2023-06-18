package com.university.stockexchange.main_ui.wallet

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.university.stockexchange.model.remote.service.ServiceApi
import com.university.stockexchange.model.remote.service.UserWallet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WalletViewModel : ViewModel() {

    private val compositeDisposable =  CompositeDisposable()
    var data: MutableLiveData<UserWallet>? = MutableLiveData()

    fun fetchQuestList(serviceApi: ServiceApi?, tokenApi: String) {
        serviceApi?.let {
            compositeDisposable.add(serviceApi.getDataWallet(tokenApi)
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

    fun getWalletData() = data
}