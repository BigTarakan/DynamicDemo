package com.bigtarakan.dynamicdemo.model

import com.google.gson.annotations.SerializedName

class HelpModelBlockLogo: BaseModelBlockBase() {
    @SerializedName("apps")
    var logos: ArrayList<Logo> = arrayListOf()
    inner class Logo {
        var text: String = ""
        var link: String = ""
        var title: String = ""
        var image: String = ""
    }
}