package com.example.myapplication3.network.response

import com.google.gson.annotations.SerializedName

class JsonArrayResponse<T> : BaseResponse() {
    @SerializedName("data")
    var list: ArrayList<T>? = null
}