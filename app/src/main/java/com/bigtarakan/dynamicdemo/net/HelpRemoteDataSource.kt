package com.bigtarakan.dynamicdemo.net

import com.bigtarakan.dynamicdemo.model.BaseModel
import com.bigtarakan.dynamicdemo.model.HelpModelBlockLogo
import com.google.gson.Gson
import io.reactivex.Observable

class HelpRemoteDataSource {
    fun getData():Observable<BaseModel> {
        return ApiService.create().getHelp().map {
            val resultModel = Gson().fromJson(it, BaseModel::class.java)
            resultModel.elements.forEach { element ->
                when (element.type) {
                    "logo_block" ->
                        resultModel.blocks.add(Gson().fromJson(element.params, HelpModelBlockLogo::class.java).apply { type = element.type })
                }
            }
            resultModel
        }
    }
}