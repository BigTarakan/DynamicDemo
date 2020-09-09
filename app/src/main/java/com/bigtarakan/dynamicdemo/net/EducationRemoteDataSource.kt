package com.bigtarakan.dynamicdemo.net

import com.bigtarakan.dynamicdemo.model.BaseModel
import com.bigtarakan.dynamicdemo.model.EducationModelBlockLink
import com.google.gson.Gson
import io.reactivex.Observable

class EducationRemoteDataSource {
    fun getData():Observable<BaseModel> {
        return ApiService.create().getEducation().map {
            val resultModel = Gson().fromJson(it, BaseModel::class.java)
            resultModel.elements.forEach { element ->
                when (element.type) {
                    "link_list_item" ->
                        resultModel.blocks.add(Gson().fromJson(element.params, EducationModelBlockLink::class.java).apply { type = element.type })
                }
            }
            resultModel
        }
    }
}