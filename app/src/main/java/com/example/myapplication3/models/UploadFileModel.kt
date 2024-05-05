package com.example.myapplication3.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UploadFileModel : BaseModel()
{
    @JvmField
    @SerializedName("url")
    @Expose
    val url: String? = null

    @SerializedName("preview")
    @Expose
    val preview: String? = null
}