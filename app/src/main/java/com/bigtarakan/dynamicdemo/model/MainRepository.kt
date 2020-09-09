package com.bigtarakan.dynamicdemo.model

import com.bigtarakan.dynamicdemo.net.MainRemoteDataSource
import io.reactivex.Observable

class MainRepository {
    fun getData(): Observable<BaseModel> {
        return MainRemoteDataSource().getData()
    }
}