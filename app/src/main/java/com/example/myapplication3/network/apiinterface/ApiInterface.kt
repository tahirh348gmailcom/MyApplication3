package com.example.myapplication3.network.apiinterface

import com.example.myapplication3.models.NotificationListModel
import com.example.myapplication3.models.UserModel
import com.example.myapplication3.network.endpoints.ApiEndpoints
import com.example.myapplication3.network.requestbuilder.RequestBuilder
import com.example.myapplication3.network.response.JsonObjectResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    //Authentication
    @POST(ApiEndpoints.AUTH_LOGIN)
    suspend fun login(
        @Body request: RequestBuilder,
    ): Response<JsonObjectResponse<UserModel>>

    @GET(ApiEndpoints.AUTH_LOGOUT)
    suspend fun logout(
    ): Response<JsonObjectResponse<String>>

    //User
    @GET(ApiEndpoints.GET_PROFILE)
    suspend fun getProfile(
        @Query("id") userId : String?,
    ): Response<JsonObjectResponse<UserModel>>

    @GET(ApiEndpoints.GET_NOTIFICATIONS_LIST)
    suspend fun notificationList(
        @Query("page") page : Int?,
        @Query("perPage") perPage : Int?,
    ) : Response<JsonObjectResponse<NotificationListModel>>

}