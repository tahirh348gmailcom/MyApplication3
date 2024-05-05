package com.example.myapplication3.network.result

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Failure(val message: String) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
}