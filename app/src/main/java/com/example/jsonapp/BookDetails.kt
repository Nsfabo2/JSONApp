package com.example.jsonapp

import com.google.gson.annotations.SerializedName

class BookDetails {

    @SerializedName("date")
    var date: String? = null

    @SerializedName("eur")
    var eur: Cur? = null

    class Cur {

        @SerializedName("kwd")
        var kwd: String? = null

        @SerializedName("sar")
        var sar: String? = null

        @SerializedName("egp")
        var egp: String? = null

        @SerializedName("aed")
        var aed: String? = null

        @SerializedName("bhd")
        var bhd: String? = null

        @SerializedName("qar")
        var qar: String? = null

    }
}