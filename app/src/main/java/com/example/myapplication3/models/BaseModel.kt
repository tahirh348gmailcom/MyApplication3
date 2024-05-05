package com.example.myapplication3.models

import androidx.databinding.BaseObservable
import java.io.Serializable


open class BaseModel : BaseObservable(), Serializable {

    var selected: Boolean = false
}
