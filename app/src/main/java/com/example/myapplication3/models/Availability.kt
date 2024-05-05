package com.example.myapplication3.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Availability : BaseModel() {
    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("start")
    @Expose
    var start: Long? = null

    @SerializedName("end")
    @Expose
    var end: Long? = null
}