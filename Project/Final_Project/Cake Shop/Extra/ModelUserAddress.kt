package com.example.e_commerce2.Extra

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ModelUserAddress {
    @Expose
    @SerializedName("uid")
    var uid=""

    @Expose
    @SerializedName("aid")
    var aid=0

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
}