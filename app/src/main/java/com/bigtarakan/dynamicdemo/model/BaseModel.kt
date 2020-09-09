package com.bigtarakan.dynamicdemo.model

import com.google.gson.JsonElement

class BaseModel {
    var title: String = ""
    var bg_color: String = ""
    var hide_navigation: Boolean = true
    var elements: ArrayList<Element> = arrayListOf()
    var blocks: ArrayList<BaseModelBlockBase> = arrayListOf()

    inner class Element {
        var type: String = ""
        var params: JsonElement? = null
    }
}