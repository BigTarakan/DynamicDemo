package com.bigtarakan.dynamicdemo.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bigtarakan.dynamicdemo.model.AboutRepository
import com.bigtarakan.dynamicdemo.model.BaseModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AboutViewModel(application: Application): AndroidViewModel(application) {

    var repositoryObservable = MutableLiveData<BaseModel>()

    fun getData() {
        AboutRepository().getData().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ data ->
                repositoryObservable.value = data
            }, {
                Log.e("@@@@@", "${it.message}\n${it.stackTrace}")
            })
    }
}