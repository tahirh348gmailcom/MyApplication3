package com.example.myapplication3.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SlotModel : BaseModel()
{
    @SerializedName("bookingId")
    @Expose
    val bookingId: String? = null

    @SerializedName("_id")
    @Expose
    val id: String? = null

    @SerializedName("startTime")
    @Expose
    var startTime: Long? = null

    @SerializedName("endTime")
    @Expose
    val endTime: Long? = null

    @SerializedName("lawyerId")
    @Expose
    val lawyerId: String? = null
}