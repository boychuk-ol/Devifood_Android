package com.example.myapplication.service

import android.util.Log
import com.example.myapplication.model.Shop
import com.example.myapplication.model.ShopResponse
import com.example.myapplication.model.ShopsResponse
import com.example.myapplication.`interface`.RetrofitAPI
import com.example.myapplication.`object`.RetrofitClient
import com.example.myapplication.adapter.ShopsAdapter
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShopService {

    private lateinit var shops: List<Shop>
    private lateinit var shop: Shop
    private val apiService = RetrofitClient.getApiClient()?.create(RetrofitAPI::class.java)

    fun shopsResponseHandling(call: Call<ShopsResponse>?, adapter: ShopsAdapter) {
        call?.enqueue(object : Callback<ShopsResponse> {
            override fun onResponse(call: Call<ShopsResponse>, response: Response<ShopsResponse>) {
                if (response.isSuccessful) {
                    val shopResponse = response.body()
                    if (shopResponse != null && !shopResponse.error) {
                        // Use Jackson ObjectMapper for deserialization
                        val objectMapper = ObjectMapper().registerKotlinModule()
                        shops = objectMapper.readValue(
                            Gson().toJson(shopResponse.data),
                            object : TypeReference<List<Shop>>() {}
                        )
                        if (shops.isNotEmpty()) {
                            adapter.setShops(shops)
                            Log.d("TESTIK2", shop.image.fullLink.toString())
                        } else {
                            Log.e("API_ERROR", "Received empty shop list")
                        }
                    } else {
                        Log.e("API_ERROR", "Error: ${shopResponse?.message}")
                    }
                } else {
                    Log.e("API_ERROR", "Response unsuccessful: ${response.errorBody()?.string()}")
                    Log.d("API_ERROR2", "onResponse: ConfigurationListener::"+call.request().url());
                }
            }

            override fun onFailure(call: Call<ShopsResponse>, t: Throwable) {
                Log.e("API_ERROR", "Network error: ${t.message}")
            }
        })
    }

    fun shopResponseHandling(call: Call<ShopResponse>?, adapter: ShopsAdapter) {
        call?.enqueue(object : Callback<ShopResponse> {
            override fun onResponse(call: Call<ShopResponse>, response: Response<ShopResponse>) {
                if (response.isSuccessful) {
                    val shopResponse = response.body()
                    if (shopResponse != null && !shopResponse.error) {
                        // Use Jackson ObjectMapper for deserialization
                        val objectMapper = ObjectMapper().registerKotlinModule()
                        shop = objectMapper.readValue(Gson().toJson(shopResponse.data), Shop::class.java)
                        adapter.addShop(shop)
                        Log.d("TESTIK", shop.image.fullLink.toString())
                    } else {
                        Log.e("API_ERROR", "Error: ${shopResponse?.message}")
                    }
                } else {
                    Log.e("API_ERROR", "Response unsuccessful: ${response.errorBody()?.string()}")
                    Log.d("API_ERROR2", "onResponse: ConfigurationListener::"+call.request().url());
                }
            }

            override fun onFailure(call: Call<ShopResponse>, t: Throwable) {
                Log.e("API_ERROR", "Network error: ${t.message}")
            }
        })
    }

    fun getShops(adapter: ShopsAdapter) {
        val call = apiService?.getShops()
        shopsResponseHandling(call, adapter)
    }

    fun getShopById(shopId: Int, adapter: ShopsAdapter) {
        val call = apiService?.getShopById(shopId)
        shopResponseHandling(call, adapter)
    }

    fun getShopsByCategory(category: String, adapter: ShopsAdapter) {
        val call = apiService?.getShopsByCategory(category)
        shopsResponseHandling(call, adapter)
    }

    fun getShopByName(name: String, adapter: ShopsAdapter) {
        val call = apiService?.getShopByName(name)
        shopResponseHandling(call, adapter)
    }

    fun getShopsWithRatingBetween(minRating: Float, maxRating: Float, adapter: ShopsAdapter) {
        val call = apiService?.getShopsWithRatingBetween(minRating, maxRating)
        shopsResponseHandling(call, adapter)
    }

    fun getShopsWithRatingLessThan(rating: Float, adapter: ShopsAdapter) {
        val call = apiService?.getShopsWithRatingLessThan(rating)
        shopsResponseHandling(call, adapter)
    }

    fun getShopsWithRatingMoreThan(rating: Float, adapter: ShopsAdapter) {
        val call = apiService?.getShopsWithRatingMoreThan(rating)
        shopsResponseHandling(call, adapter)
    }

    fun getShopsWithReviewsBetween(minReviews: Int, maxReviews: Int, adapter: ShopsAdapter) {
        val call = apiService?.getShopsWithReviewsBetween(minReviews, maxReviews)
        shopsResponseHandling(call, adapter)
    }

    fun getShopsWithReviewsLessThan(reviews: Int, adapter: ShopsAdapter) {
        val call = apiService?.getShopsWithReviewsLessThan(reviews)
        shopsResponseHandling(call, adapter)
    }

    fun getShopsWithReviewsMoreThan(reviews: Int, adapter: ShopsAdapter) {
        val call = apiService?.getShopsWithReviewsMoreThan(reviews)
        shopsResponseHandling(call, adapter)
    }

}
