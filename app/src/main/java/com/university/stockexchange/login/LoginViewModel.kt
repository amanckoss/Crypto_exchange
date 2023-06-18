package com.university.stockexchange.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.university.stockexchange.model.remote.service.ServiceApi
import com.university.stockexchange.model.remote.service.model.AuthData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginViewModel: ViewModel() {
    private val compositeDisposable =  CompositeDisposable()
    var data: MutableLiveData<AuthData>? = MutableLiveData()

    fun fetchQuestList(serviceApi: ServiceApi?, email: String?) {
        serviceApi?.let {
            compositeDisposable.add(serviceApi.postLogin(email)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({data ->
                        Log.d("f","complete")
                        this.data?.value = data
                    }, {throwable ->
                        Log.d("f","failed on this")
                        Log.d("f",throwable.toString())
                    }))
        }
    }

    fun getLoginData() = data
}