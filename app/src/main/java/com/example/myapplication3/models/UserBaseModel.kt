package com.example.myapplication3.data.models

import com.example.myapplication3.models.BaseModel
import com.example.myapplication3.models.UserModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserBaseModel : BaseModel(){

    @SerializedName("isLoggedIn")
    @Expose
    val isLoggedIn: Boolean? = null

    @SerializedName("token")
    @Expose
    val token: String? = null

    @SerializedName("user")
    @Expose
    var user: UserModel? = null

}