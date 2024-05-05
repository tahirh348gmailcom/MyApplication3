package com.example.myapplication3.models

import androidx.annotation.DrawableRes

data class DataModel(

    var id: Int? = null,
    var itemCount: Int = 1,
    @DrawableRes
    var color: Int? = null,
    var image: Int? = null,
    var coins: Int? = null,
    var price: Int? = null,
    var date: String? = null,
    var message: String? = null,
    var key: String? = null,

    ) : BaseModel() {

}