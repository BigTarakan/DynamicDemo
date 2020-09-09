package com.bigtarakan.dynamicdemo.net

import com.bigtarakan.dynamicdemo.model.BaseModel
import com.bigtarakan.dynamicdemo.model.MainModelBlockLinks
import com.bigtarakan.dynamicdemo.model.MainModelBlockProfile
import com.google.gson.Gson
import io.reactivex.Observable

class MainRemoteDataSource {
    fun getData():Observable<BaseModel> {
        return ApiService.create().getMain().map {
            val resultModel = Gson().fromJson(it, BaseModel::class.java)
            resultModel.elements.forEach { element ->
                when (element.type) {
                    "profile_block" ->
                        resultModel.blocks.add(Gson().fromJson(element.params, MainModelBlockProfile::class.java).apply { type = element.type })
                    "links_block" ->
                        resultModel.blocks.add(Gson().fromJson(element.params, MainModelBlockLinks::class.java).apply { type = element.type })
                }
            }
            resultModel
        }
    }
}