package com.example.myapplication3.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PushNotificationModel : BaseModel()
{
    @SerializedName("data")
    @Expose
    val data: Data? = null

    class Data : BaseModel(){
        @SerializedName("title")
        @Expose
        var title: String? = null

        @SerializedName("body")
        @Expose
        var body: String? = null

        @SerializedName("type")
        @Expose
        var type: String? = null

        @SerializedName("bookingId")
        @Expose
        var bookingId: String? = null

        @SerializedName("callerImage")
        @Expose
        var callerImage: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("appoID")
        @Expose
        var appoID: String? = null

        @SerializedName("roomSid")
        @Expose
        var roomSid: String? = null

        @SerializedName("token")
        @Expose
        var token: String? = null

        @SerializedName("roomName")
        @Expose
        var roomName: String? = null
    }
}