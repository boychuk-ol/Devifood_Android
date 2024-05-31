package com.example.myapplication.`object`

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    final val BASE_URL: String = "http://192.168.1.136/devifood/"

    var retrofit: Retrofit? = null

    fun getApiClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonHelper.createGson()))
                .build()
        }
        return retrofit
    }

}