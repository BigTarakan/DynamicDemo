package com.bigtarakan.dynamicdemo.model

import com.bigtarakan.dynamicdemo.net.HelpRemoteDataSource
import io.reactivex.Observable

class HelpRepository {
    fun getData(): Observable<BaseModel> {
        return HelpRemoteDataSource().getData()
    }
}