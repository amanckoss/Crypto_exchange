package com.university.stockexchange.registration

import android.text.Editable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.university.stockexchange.model.remote.service.ServiceApi
import com.university.stockexchange.model.remote.service.model.RegistrationData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RegistrationViewModel : ViewModel() {
    private val compositeDisposable =  CompositeDisposable()
    var data: MutableLiveData<RegistrationData>? = MutableLiveData()

    fun fetchQuestList(serviceApi: ServiceApi?, name: String?, surname: String?, email: String?, password: String?, password_conf: String) {
        Log.d("f", "start")
        serviceApi?.let {
            compositeDisposable.add(serviceApi.postRegistration(name, surname, email, password, password_conf)
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

    fun getRegisterData() = data
}