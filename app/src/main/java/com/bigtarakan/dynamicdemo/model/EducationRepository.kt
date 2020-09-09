package com.bigtarakan.dynamicdemo.model

import com.bigtarakan.dynamicdemo.net.EducationRemoteDataSource
import io.reactivex.Observable

class EducationRepository {
    fun getData(): Observable<BaseModel> {
        return EducationRemoteDataSource().getData()
    }
}