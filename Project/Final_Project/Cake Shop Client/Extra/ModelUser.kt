package com.example.e_commerce2client.Extra

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ModelUser {
    @Expose
    @SerializedName("uid")
    var uid=0

    @Expose
    @SerializedName("name")
    var username=""

    @Expose
    @SerializedName("userid")
    var userid=""

    @Expose
    @SerializedName("sid")
    var sid=""


    @Expose
    @SerializedName("email")
    var email=""

    @Expose
    @SerializedName("image")
    var image=""

    @Expose
    @SerializedName("number")
    var number=""

}
