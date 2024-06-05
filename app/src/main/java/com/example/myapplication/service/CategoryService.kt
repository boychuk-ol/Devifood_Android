package com.example.myapplication.service

import android.util.Log
import com.example.myapplication.`interface`.RetrofitAPI
import com.example.myapplication.model.CategoriesResponse
import com.example.myapplication.model.Category
import com.example.myapplication.model.CategoryResponse
import com.example.myapplication.model.Image
import com.example.myapplication.model.ImageResponse
import com.example.myapplication.model.ImagesResponse
import com.example.myapplication.`object`.RetrofitClient
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class CategoryService {

    private val apiService = RetrofitClient.getApiClient()?.create(RetrofitAPI::class.java)

    private fun parseAndHandleCategory(response: Response<CategoryResponse>?): Category? {
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
            objectMapper.readValue(Gson().toJson(responseBody.data), Category::class.java)
        } else {
            Log.e("API_ERROR", "Error: ${responseBody?.message}")
            null
        }
    }

    private fun parseAndHandleCategories(response: Response<CategoriesResponse>?): ArrayList<Category>? {
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
                object : TypeReference<ArrayList<Category>>() {}
            )
        } else {
            Log.e("API_ERROR", "Error: ${responseBody?.message}")
            null
        }
    }

//    suspend fun getCategories(): ArrayList<Category>? {
//        return withContext(Dispatchers.IO) {
//            try {
//                val call = apiService?.getImages()
//                val response = call?.execute()
//                parseAndHandleCategories(response)
//            } catch (e: Exception) {
//                Log.e("API_ERROR", "Network error: ${e.message}")
//                null
//            }
//        }
//    }

    suspend fun getCategoryById(categoryID: Int): Category? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getCategoryById(categoryID)
                val response = call?.execute()
                parseAndHandleCategory(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
}