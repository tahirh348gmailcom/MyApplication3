package com.example.myapplication3.network.response

import org.jetbrains.annotations.Nullable
import com.example.myapplication3.models.MyExtraData

// A generic class that contains response and code about loading this response.
data class Resource<out T>(val code: Int, @Nullable val response: T, var message: String, val extraData: MyExtraData) {

}
