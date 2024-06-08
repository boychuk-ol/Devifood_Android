package com.example.myapplication.service

import android.util.Log
import com.example.myapplication.model.Image
import com.example.myapplication.model.ImageResponse
import com.example.myapplication.model.ImagesResponse
import com.example.myapplication.`interface`.RetrofitAPI
import com.example.myapplication.`object`.RetrofitClient
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ImageService {
    private val apiService = RetrofitClient.getApiClient()?.create(RetrofitAPI::class.java)

    private fun parseAndHandleImage(response: Response<ImageResponse>?): Image? {
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
            objectMapper.readValue(Gson().toJson(responseBody.data), Image::class.java)
        } else {
            Log.e("API_ERROR", "Error: ${responseBody?.message}")
            null
        }
    }

    private fun parseAndHandleImages(response: Response<ImagesResponse>?): ArrayList<Image>? {
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
                object : TypeReference<ArrayList<Image>>() {}
            )
        } else {
            Log.e("API_ERROR", "Error: ${responseBody?.message}")
            null
        }
    }

    suspend fun getImages(): ArrayList<Image>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getImages()
                val response = call?.execute()
                parseAndHandleImages(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }

    suspend fun getImageById(imageId: Int): Image? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getImageById(imageId)
                val response = call?.execute()
                parseAndHandleImage(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }

    suspend fun getImageByFullLink(fullLink: String): Image? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getImageByFullLink(fullLink)
                val response = call?.execute()
                parseAndHandleImage(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }

    suspend fun getImageByName(name: String): Image? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getImageByName(name)
                val response = call?.execute()
                parseAndHandleImage(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }


    suspend fun getImagesByEntity(entity: String): ArrayList<Image>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getImagesByEntity(entity)
                val response = call?.execute()
                parseAndHandleImages(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }

    suspend fun getImagesByExtension(extension: String): ArrayList<Image>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getImagesByExtension(extension)
                val response = call?.execute()
                parseAndHandleImages(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
}