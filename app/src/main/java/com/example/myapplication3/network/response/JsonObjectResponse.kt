package com.example.myapplication3.network.response

import com.google.gson.annotations.SerializedName

class JsonObjectResponse<T> : BaseResponse() {
    @SerializedName("data")
    var data: T? = null
}