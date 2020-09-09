package com.bigtarakan.dynamicdemo.model

import com.bigtarakan.dynamicdemo.net.AboutRemoteDataSource
import io.reactivex.Observable

class AboutRepository {
    fun getData(): Observable<BaseModel> {
        return AboutRemoteDataSource().getData()
    }
}