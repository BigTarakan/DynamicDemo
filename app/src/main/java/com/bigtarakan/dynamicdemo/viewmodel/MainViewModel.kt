package com.bigtarakan.dynamicdemo.viewmodel

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bigtarakan.dynamicdemo.model.BaseModel
import com.bigtarakan.dynamicdemo.model.MainRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(application: Application): AndroidViewModel(application) {

    var repository = ObservableField<BaseModel>()
    var repositoryObservable = MutableLiveData<BaseModel>()

    fun getData() {
        MainRepository().getData().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ data ->
                repository.set(data)
                repositoryObservable.value = data
            }, {
                Log.e("@@@@@", "${it.message}\n${it.stackTrace}")
            })
    }
}