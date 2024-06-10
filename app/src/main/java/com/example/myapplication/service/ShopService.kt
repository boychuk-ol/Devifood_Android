package com.example.myapplication.service

import android.util.Log
import com.example.myapplication.model.Shop
import com.example.myapplication.model.ShopResponse
import com.example.myapplication.model.ShopsResponse
import com.example.myapplication.`interface`.RetrofitAPI
import com.example.myapplication.`object`.RetrofitClient
import com.example.myapplication.adapter.ShopsAdapter
import com.example.myapplication.model.Order
import com.example.myapplication.model.OrderResponse
import com.example.myapplication.model.OrdersResponse
import com.example.myapplication.model.Product
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShopService {

    private lateinit var shops: List<Shop>
    private lateinit var shop: Shop
    private val apiService = RetrofitClient.getApiClient()?.create(RetrofitAPI::class.java)

//    fun shopsResponseHandling(call: Call<ShopsResponse>?, adapter: ShopsAdapter) {
//        call?.enqueue(object : Callback<ShopsResponse> {
//            override fun onResponse(call: Call<ShopsResponse>, response: Response<ShopsResponse>) {
//                if (response.isSuccessful) {
//                    val shopResponse = response.body()
//                    if (shopResponse != null && !shopResponse.error) {
//                        // Use Jackson ObjectMapper for deserialization
//                        val objectMapper = ObjectMapper().registerKotlinModule()
//                        shops = objectMapper.readValue(
//                            Gson().toJson(shopResponse.data),
//                            object : TypeReference<ArrayList<Shop>>() {}
//                        )
//                        if (shops.isNotEmpty()) {
//                            adapter.setShops(shops)
//                            Log.d("TESTIK2", shop.image.fullLink.toString())
//                        } else {
//                            Log.e("API_ERROR", "Received empty shop list")
//                        }
//                    } else {
//                        Log.e("API_ERROR", "Error: ${shopResponse?.message}")
//                    }
//                } else {
//                    Log.e("API_ERROR", "Response unsuccessful: ${response.errorBody()?.string()}")
//                    Log.d("API_ERROR2", "onResponse: ConfigurationListener::"+call.request().url());
//                }
//            }
//
//            override fun onFailure(call: Call<ShopsResponse>, t: Throwable) {
//                Log.e("API_ERROR", "Network error: ${t.message}")
//            }
//        })
//    }
//
//    fun shopResponseHandling(call: Call<ShopResponse>?, adapter: ShopsAdapter) {
//        call?.enqueue(object : Callback<ShopResponse> {
//            override fun onResponse(call: Call<ShopResponse>, response: Response<ShopResponse>) {
//                if (response.isSuccessful) {
//                    val shopResponse = response.body()
//                    if (shopResponse != null && !shopResponse.error) {
//                        // Use Jackson ObjectMapper for deserialization
//                        val objectMapper = ObjectMapper().registerKotlinModule()
//                        shop = objectMapper.readValue(Gson().toJson(shopResponse.data), Shop::class.java)
//                        adapter.addShop(shop)
//                        Log.d("TESTIK", shop.image.fullLink.toString())
//                    } else {
//                        Log.e("API_ERROR", "Error: ${shopResponse?.message}")
//                    }
//                } else {
//                    Log.e("API_ERROR", "Response unsuccessful: ${response.errorBody()?.string()}")
//                    Log.d("API_ERROR2", "onResponse: ConfigurationListener::"+call.request().url());
//                }
//            }
//
//            override fun onFailure(call: Call<ShopResponse>, t: Throwable) {
//                Log.e("API_ERROR", "Network error: ${t.message}")
//            }
//        })
//    }

    private fun parseAndHandleShop(response: Response<ShopResponse>?): Shop? {
        if (response == null) {
            Log.e("API_ERROR", "Response is null")
            return null
        }
        if (!response.isSuccessful) {
            Log.e("API_ERROR", "Response unsuccessful: ${response.errorBody()?.string()}")
            return null
        }
        val responseBody = response.body()
        return if (responseBody != null && !responseBody.error) {
            val objectMapper = jacksonObjectMapper().registerKotlinModule()
            objectMapper.readValue(Gson().toJson(responseBody.data), Shop::class.java)
        } else {
            Log.e("API_ERROR", "Error: ${responseBody?.message}")
            null
        }
    }
    private fun parseAndHandleShops(response: Response<ShopsResponse>?): ArrayList<Shop>? {
        if (response == null) {
            Log.e("API_ERROR", "Response is null")
            return null
        }
        if (!response.isSuccessful) {
            Log.e("API_ERROR", "Response unsuccessful: ${response.errorBody()?.string()}")
            return null
        }
        val responseBody = response.body()
        return if (responseBody != null && !responseBody.error) {
            val objectMapper = jacksonObjectMapper().registerKotlinModule()
            objectMapper.readValue(
                Gson().toJson(responseBody.data),
                object : TypeReference<ArrayList<Shop>>() {}
            )
        } else {
            Log.e("API_ERROR", "Error: ${responseBody?.message}")
            null
        }
    }
    suspend fun getShops(): ArrayList<Shop>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getShops()
                val response = call?.execute()
                parseAndHandleShops(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getShopById(shopID: Int): Shop? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getShopById(shopID)
                val response = call?.execute()
                parseAndHandleShop(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getShopByName(name: String): Shop? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getShopByName(name)
                val response = call?.execute()
                parseAndHandleShop(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getShopByProduct(productID: String): Shop? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getShopByProduct(productID)
                val response = call?.execute()
                parseAndHandleShop(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getShopByAddress(address: String): Shop? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getShopByAddress(address)
                val response = call?.execute()
                parseAndHandleShop(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getShopsByCategory(categoryID: Int): ArrayList<Shop>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getShopsByCategory(categoryID)
                val response = call?.execute()
                parseAndHandleShops(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getShopsByCity(city: String): ArrayList<Shop>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getShopsByCity(city)
                val response = call?.execute()
                parseAndHandleShops(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getShopByNeighborhood(neighborhood: String): ArrayList<Shop>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getShopsByNeighborhood(neighborhood)
                val response = call?.execute()
                parseAndHandleShops(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getShopByStreet(street: String): ArrayList<Shop>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getShopsByStreet(street)
                val response = call?.execute()
                parseAndHandleShops(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getShopByCity(city: String): ArrayList<Shop>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getShopsByCity(city)
                val response = call?.execute()
                parseAndHandleShops(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getShopByRating(rating: Float): ArrayList<Shop>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getShopsByRating(rating)
                val response = call?.execute()
                parseAndHandleShops(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getShopByReviews(reviews: Int): ArrayList<Shop>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getShopsByReviews(reviews)
                val response = call?.execute()
                parseAndHandleShops(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getShopBySubcategory(subcategory: String): ArrayList<Shop>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getShopsBySubcategory(subcategory)
                val response = call?.execute()
                parseAndHandleShops(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getShopBySubsubcategory(subsubcategory: String): ArrayList<Shop>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getShopsBySubsubcategory(subsubcategory)
                val response = call?.execute()
                parseAndHandleShops(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getShopsWithRatingBetween(minRating: Float, maxRating: Float): ArrayList<Shop>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getShopsWithRatingBetween(minRating, maxRating)
                val response = call?.execute()
                parseAndHandleShops(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getShopsWithRatingMoreThan(rating: Float): ArrayList<Shop>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getShopsWithRatingMoreThan(rating)
                val response = call?.execute()
                parseAndHandleShops(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getShopsWithRatingLessThan(rating: Float): ArrayList<Shop>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getShopsWithRatingLessThan(rating)
                val response = call?.execute()
                parseAndHandleShops(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getShopsWithReviewsBetween(minRating: Float, maxRating: Float): ArrayList<Shop>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getShopsWithRatingBetween(minRating, maxRating)
                val response = call?.execute()
                parseAndHandleShops(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getShopsWithReviewsMoreThan(rating: Float): ArrayList<Shop>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getShopsWithRatingMoreThan(rating)
                val response = call?.execute()
                parseAndHandleShops(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getShopsWithReviewsLessThan(rating: Float): ArrayList<Shop>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getShopsWithRatingLessThan(rating)
                val response = call?.execute()
                parseAndHandleShops(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }

//    fun getShops(adapter: ShopsAdapter) {
//        val call = apiService?.getShops()
//        shopsResponseHandling(call, adapter)
//    }
//
//    fun getShopById(shopId: Int, adapter: ShopsAdapter) {
//        val call = apiService?.getShopById(shopId)
//        shopResponseHandling(call, adapter)
//    }
//
//    fun getShopsByCategory(category: String, adapter: ShopsAdapter) {
//        val call = apiService?.getShopsByCategory(category)
//        shopsResponseHandling(call, adapter)
//    }
//
//    fun getShopByName(name: String, adapter: ShopsAdapter) {
//        val call = apiService?.getShopByName(name)
//        shopResponseHandling(call, adapter)
//    }
//
//    fun getShopsWithRatingBetween(minRating: Float, maxRating: Float, adapter: ShopsAdapter) {
//        val call = apiService?.getShopsWithRatingBetween(minRating, maxRating)
//        shopsResponseHandling(call, adapter)
//    }
//
//    fun getShopsWithRatingLessThan(rating: Float, adapter: ShopsAdapter) {
//        val call = apiService?.getShopsWithRatingLessThan(rating)
//        shopsResponseHandling(call, adapter)
//    }
//
//    fun getShopsWithRatingMoreThan(rating: Float, adapter: ShopsAdapter) {
//        val call = apiService?.getShopsWithRatingMoreThan(rating)
//        shopsResponseHandling(call, adapter)
//    }
//
//    fun getShopsWithReviewsBetween(minReviews: Int, maxReviews: Int, adapter: ShopsAdapter) {
//        val call = apiService?.getShopsWithReviewsBetween(minReviews, maxReviews)
//        shopsResponseHandling(call, adapter)
//    }
//
//    fun getShopsWithReviewsLessThan(reviews: Int, adapter: ShopsAdapter) {
//        val call = apiService?.getShopsWithReviewsLessThan(reviews)
//        shopsResponseHandling(call, adapter)
//    }
//
//    fun getShopsWithReviewsMoreThan(reviews: Int, adapter: ShopsAdapter) {
//        val call = apiService?.getShopsWithReviewsMoreThan(reviews)
//        shopsResponseHandling(call, adapter)
//    }

}
