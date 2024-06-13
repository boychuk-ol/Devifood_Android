package com.example.myapplication.`object`

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    final val CLOUD_STORAGE_PRODUCT_IMAGES_URL: String = "https://storage.googleapis.com/devifood_images/images/"
    final val CLOUD_STORAGE_SHOP_IMAGES_URL: String = "https://storage.googleapis.com/devifood_images/shopImages/"
    final val BASE_URL: String = "http://devifood.rf.gd/"
    final val LOCALHOST_URL: String = "http://192.168.1.136/devifood/" // spare, not hosted

    var retrofit: Retrofit? = null

    fun getApiClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder().baseUrl(LOCALHOST_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonHelper.createGson()))
                .build()
        }
        return retrofit
    }

}