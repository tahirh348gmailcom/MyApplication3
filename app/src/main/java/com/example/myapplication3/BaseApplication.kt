package com.example.myapplication3

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.myapplication3.network.apiinterface.ApiService
import com.example.myapplication3.utils.Constants
import com.example.myapplication3.utils.CustomInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class BaseApplication : MultiDexApplication() {

    lateinit var apiService: ApiService

    companion object {
        lateinit var instance: BaseApplication
        lateinit var mContext: Context
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        mContext = applicationContext
        retrofit()
    }

    private fun retrofit() {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.MINUTES)
            .writeTimeout(10, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .addInterceptor(CustomInterceptor(""))
            //.addInterceptor(HttpLoggingInterceptor())

        val okHttpClient = builder.build()
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java!!)
    }

}