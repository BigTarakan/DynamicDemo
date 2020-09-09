package com.bigtarakan.dynamicdemo.model

class MainModelBlockLinks: BaseModelBlockBase() {
    var links: ArrayList<Link> = arrayListOf()

    inner class Link {
        var link: String = ""
        var title: String = ""
        var image: String = ""
    }
}