package com.example.myapplication.service

import android.util.Log
import com.example.myapplication.`interface`.RetrofitAPI
import com.example.myapplication.model.Image
import com.example.myapplication.model.ImageResponse
import com.example.myapplication.model.ImagesResponse
import com.example.myapplication.model.Location
import com.example.myapplication.model.LocationResponse
import com.example.myapplication.model.LocationsResponse
import com.example.myapplication.`object`.RetrofitClient
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class LocationService {
    private val apiService = RetrofitClient.getApiClient()?.create(RetrofitAPI::class.java)
    private fun parseAndHandleLocation(response: Response<LocationResponse>?): Location? {
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
            objectMapper.readValue(Gson().toJson(responseBody.data), Location::class.java)
        } else {
            Log.e("API_ERROR", "Error: ${responseBody?.message}")
            null
        }
    }
    private fun parseAndHandleLocations(response: Response<LocationsResponse>?): ArrayList<Location>? {
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
                object : TypeReference<ArrayList<Location>>() {}
            )
        } else {
            Log.e("API_ERROR", "Error: ${responseBody?.message}")
            null
        }
    }

    suspend fun getLocationsMaxId(): Int? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getLocationsMaxId()
                val response = call?.execute()
                if (response == null) {
                    Log.e("API_ERROR", "Response is null")
                    return@withContext null
                }
                if (!response.isSuccessful) {
                    Log.e("API_ERROR", "Response unsuccessful: ${response.errorBody()?.string()}")
                    return@withContext null
                }
                return@withContext response.body()?.locationId
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                return@withContext  null
            }
        }
    }
    suspend fun getLocations(): ArrayList<Location>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getLocations()
                val response = call?.execute()
                parseAndHandleLocations(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getLocationById(locationID: Int): Location? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getLocationById(locationID)
                val response = call?.execute()
                parseAndHandleLocation(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getLocationByFullAddress(fullAddress: String): Location? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getLocationByFullAddress(fullAddress)
                val response = call?.execute()
                parseAndHandleLocation(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getLocationsByCity(city: String): ArrayList<Location>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getLocationsByCity(city)
                val response = call?.execute()
                parseAndHandleLocations(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getLocationsByNeighborhood(neighborhood: String): ArrayList<Location>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getLocationsByNeighborhood(neighborhood)
                val response = call?.execute()
                parseAndHandleLocations(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getLocationsByStreet(street: String): ArrayList<Location>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getLocationsByStreet(street)
                val response = call?.execute()
                parseAndHandleLocations(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getLocationsByStreetNumber(streetNumber: String): ArrayList<Location>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getLocationsByStreetNumber(streetNumber)
                val response = call?.execute()
                parseAndHandleLocations(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
}