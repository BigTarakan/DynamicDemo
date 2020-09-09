package com.bigtarakan.dynamicdemo.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bigtarakan.dynamicdemo.model.BaseModel
import com.bigtarakan.dynamicdemo.model.EducationRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EducationViewModel(application: Application): AndroidViewModel(application) {

    var repositoryObservable = MutableLiveData<BaseModel>()

    fun getData() {
        EducationRepository().getData().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ data ->
                repositoryObservable.value = data
            }, {
                Log.e("@@@@@", "${it.message}\n${it.stackTrace}")
            })
    }
}