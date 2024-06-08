package com.example.myapplication.service

import android.util.Log
import com.example.myapplication.`interface`.RetrofitAPI
import com.example.myapplication.model.Courier
import com.example.myapplication.model.CourierResponse
import com.example.myapplication.model.CouriersResponse
import com.example.myapplication.`object`.RetrofitClient
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class CourierService {
    private val apiService = RetrofitClient.getApiClient()?.create(RetrofitAPI::class.java)

    private fun parseAndHandleCourier(response: Response<CourierResponse>?): Courier? {
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
            objectMapper.readValue(Gson().toJson(responseBody.data), Courier::class.java)
        } else {
            Log.e("API_ERROR", "Error: ${responseBody?.message}")
            null
        }
    }

    private fun parseAndHandleCouriers(response: Response<CouriersResponse>?): ArrayList<Courier>? {
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
                object : TypeReference<ArrayList<Courier>>() {}
            )
        } else {
            Log.e("API_ERROR", "Error: ${responseBody?.message}")
            null
        }
    }
    suspend fun getCourierById(courierID: Int): Courier? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getCourierById(courierID)
                val response = call?.execute()
                parseAndHandleCourier(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getCouriers(): List<Courier>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getCouriers()
                val response = call?.execute()
                parseAndHandleCouriers(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getCourierByEmail(email: String): Courier? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getCourierByEmail(email)
                val response = call?.execute()
                parseAndHandleCourier(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getCourierByFullName(fullName: String): Courier? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getCourierByFullName(fullName)
                val response = call?.execute()
                parseAndHandleCourier(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getCourierByPhone(phone: String): Courier? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getCourierByPhone(phone)
                val response = call?.execute()
                parseAndHandleCourier(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getCouriersByWorkArea(workArea: String): List<Courier>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getCouriersByWorkArea(workArea)
                val response = call?.execute()
                parseAndHandleCouriers(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getCouriersByWorkRegion(workRegion: String): List<Courier>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getCouriersByWorkRegion(workRegion)
                val response = call?.execute()
                parseAndHandleCouriers(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
}