package com.example.myapplication3.network.endpoints

object ApiEndpoints {

    //Prefix
    private const val PREFIX_AUTH = "auth/"
    private const val PREFIX_USERS = "users/"

    //Authentication
    const val AUTH_LOGIN = PREFIX_AUTH + "log-in"
    const val AUTH_LOGOUT = PREFIX_AUTH + "log-out"

    //User
    const val GET_PROFILE = PREFIX_USERS + "profile"
    const val GET_NOTIFICATIONS_LIST = PREFIX_USERS + "notifications"

}