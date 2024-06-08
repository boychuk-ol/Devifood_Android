package com.example.myapplication.service

import android.util.Log
import com.example.myapplication.`interface`.RetrofitAPI
import com.example.myapplication.model.CategoriesResponse
import com.example.myapplication.model.Category
import com.example.myapplication.model.CategoryResponse
import com.example.myapplication.model.Client
import com.example.myapplication.model.ClientResponse
import com.example.myapplication.model.ClientsResponse
import com.example.myapplication.`object`.RetrofitClient
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ClientService {

    private val apiService = RetrofitClient.getApiClient()?.create(RetrofitAPI::class.java)

    private fun parseAndHandleClient(response: Response<ClientResponse>?): Client? {
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
            objectMapper.readValue(Gson().toJson(responseBody.data), Client::class.java)
        } else {
            Log.e("API_ERROR", "Error: ${responseBody?.message}")
            null
        }
    }

    private fun parseAndHandleClients(response: Response<ClientsResponse>?): ArrayList<Client>? {
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
                object : TypeReference<ArrayList<Client>>() {}
            )
        } else {
            Log.e("API_ERROR", "Error: ${responseBody?.message}")
            null
        }
    }
    suspend fun getClientById(clientID: Int): Client? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getClientById(clientID)
                val response = call?.execute()
                parseAndHandleClient(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getClients(): List<Client>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getClients()
                val response = call?.execute()
                parseAndHandleClients(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getClientByPhone(phone: String): Client? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getClientByPhone(phone)
                val response = call?.execute()
                parseAndHandleClient(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getCategoriesByEmail(email: String): Client? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getClientByEmail(email)
                val response = call?.execute()
                parseAndHandleClient(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getClientsByAddress(address: String): List<Client>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getClientsByAddress(address)
                val response = call?.execute()
                parseAndHandleClients(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getClientsByCity(city: String): List<Client>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getClientsByCity(city)
                val response = call?.execute()
                parseAndHandleClients(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getClientsByFullName(fullName: String): List<Client>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getClientsByFullName(fullName)
                val response = call?.execute()
                parseAndHandleClients(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getClientsByNeighborhood(neighborhood: String): List<Client>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getClientsByNeighborhood(neighborhood)
                val response = call?.execute()
                parseAndHandleClients(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getClientsByStreet(street: String): List<Client>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getClientsByStreet(street)
                val response = call?.execute()
                parseAndHandleClients(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
}