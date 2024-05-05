package com.example.myapplication3.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class CommonDataModel : BaseModel()
{
    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("isSuspended")
    @Expose
    var isSuspended: Boolean? = null

    @SerializedName("isDeleted")
    @Expose
    var isDeleted: Boolean? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("name_ar")
    @Expose
    var nameAr: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("created")
    @Expose
    var created: String? = null

    @SerializedName("updated")
    @Expose
    var updated: String? = null

    @SerializedName("__v")
    @Expose
    var v: Int? = null

    @SerializedName("dial_code")
    @Expose
    public var dialCode: String? = null

}