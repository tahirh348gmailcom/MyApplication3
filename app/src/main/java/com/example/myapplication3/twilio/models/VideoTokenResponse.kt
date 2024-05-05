package com.example.myapplication3.twilio.models

import com.example.myapplication3.models.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class VideoTokenResponse : BaseModel()
{
    @SerializedName("identity")
    @Expose
    val identity: String? = null

    @SerializedName("user_token")
    @Expose
    val token: String? = null

    @SerializedName("room_name")
    @Expose
    val roomName: String? = null

    @SerializedName("room_sid")
    @Expose
    val roomSid: String? = null

    @SerializedName("timeLimit")
    @Expose
    val timeLimit: Double? = null
}