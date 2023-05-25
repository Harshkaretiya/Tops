package com.example.e_commerce2.Extra

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ModelOrder {

    @Expose
    @SerializedName("pid")
    var pid = 0

    @Expose
    @SerializedName("oid")
    var oid = 0


    @Expose
    @SerializedName("uid")
    var uid = ""

    @Expose
    @SerializedName("sid")
    var sid = ""


    @Expose
    @SerializedName("aid")
    var aid = 0

    @Expose
    @SerializedName("qty")
    var qty = 0

    @Expose
    @SerializedName("name")
    var name = ""

    @Expose
    @SerializedName("price")
    var price = ""

    @Expose
    @SerializedName("tprice")
    var totalprice = 0

    @Expose
    @SerializedName("image")
    var image = ""
    @Expose
    @SerializedName("image2")
    var image2 = ""
    @Expose
    @SerializedName("image3")
    var image3 = ""
    @Expose
    @SerializedName("image4")
    var image4 = ""
    @Expose
    @SerializedName("image5")
    var image5 = ""

    @Expose
    @SerializedName("weight")
    var weight = ""

    @Expose
    @SerializedName("description")
    var desc = ""

    @Expose
    @SerializedName("street")
    var street=""

    @Expose
    @SerializedName("city")
    var city=""

    @Expose
    @SerializedName("state")
    var state=""

    @Expose
    @SerializedName("zipcode")
    var zipcode=""

    @Expose
    @SerializedName("country")
    var country=""

    @Expose
    @SerializedName("status")
    var status=""

}