package com.bigtarakan.dynamicdemo.net

import com.bigtarakan.dynamicdemo.model.*
import com.google.gson.Gson
import io.reactivex.Observable

class AboutRemoteDataSource {
    fun getData():Observable<BaseModel> {
        return ApiService.create().getAbout().map {
            val resultModel = Gson().fromJson(it, BaseModel::class.java)
            resultModel.elements.forEach { element ->
                when (element.type) {
                    "logo_block" ->
                        resultModel.blocks.add(Gson().fromJson(element.params, AboutModelBlockLogo::class.java).apply { type = element.type })
                    "link_list_item" ->
                        resultModel.blocks.add(Gson().fromJson(element.params, AboutModelBlockLink::class.java).apply { type = element.type })
                    "blue_link_block" ->
                        resultModel.blocks.add(Gson().fromJson(element.params, AboutModelBlockBlueLink::class.java).apply { type = element.type })
                    "copyright_block" ->
                        resultModel.blocks.add(Gson().fromJson(element.params, AboutModelBlockCopyright::class.java).apply { type = element.type })
                }
            }
            resultModel
        }
    }
}