package com.university.stockexchange.password_auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.university.stockexchange.model.remote.service.ServiceApi
import com.university.stockexchange.model.remote.service.model.AuthData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PasswordViewModel: ViewModel() {
    private val compositeDisposable =  CompositeDisposable()
    var data: MutableLiveData<AuthData>? = MutableLiveData()

    fun fetchQuestList(serviceApi: ServiceApi?, password: String?, api_token: String?) {
        serviceApi?.let {
            compositeDisposable.add(serviceApi.postPassword(password, api_token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({data ->
                        Log.d("f","complete")
                        this.data?.value = data
                    }, {throwable ->
                        Log.d("f","failed")
                        Log.d("f",throwable.toString())
                    }))
        }
    }

    fun getPasswordData() = data
}