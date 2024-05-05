package com.example.myapplication3.network.response

import com.google.gson.annotations.SerializedName

open class BaseResponse {
    @field:SerializedName("status")
    var status: String? = null

    @field:SerializedName("message")
    var message: String? = null

    @field:SerializedName("availabilitySlotId")
    var availabilitySlotId: String? = null
}
