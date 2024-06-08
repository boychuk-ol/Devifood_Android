package com.example.myapplication.service

import android.util.Log
import com.example.myapplication.`interface`.RetrofitAPI
import com.example.myapplication.model.Location
import com.example.myapplication.model.LocationResponse
import com.example.myapplication.model.LocationsResponse
import com.example.myapplication.model.Order
import com.example.myapplication.model.OrderResponse
import com.example.myapplication.model.OrdersResponse
import com.example.myapplication.`object`.RetrofitClient
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.util.Date

class OrderService {
    private val apiService = RetrofitClient.getApiClient()?.create(RetrofitAPI::class.java)
    private fun parseAndHandleOrder(response: Response<OrderResponse>?): Order? {
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
            objectMapper.readValue(Gson().toJson(responseBody.data), Order::class.java)
        } else {
            Log.e("API_ERROR", "Error: ${responseBody?.message}")
            null
        }
    }
    private fun parseAndHandleOrders(response: Response<OrdersResponse>?): ArrayList<Order>? {
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
                object : TypeReference<ArrayList<Order>>() {}
            )
        } else {
            Log.e("API_ERROR", "Error: ${responseBody?.message}")
            null
        }
    }
    suspend fun getOrders(): ArrayList<Order>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getOrders()
                val response = call?.execute()
                parseAndHandleOrders(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getOrdersById(orderID: Int): Order? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getOrderById(orderID)
                val response = call?.execute()
                parseAndHandleOrder(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getOrdersByClient(clientID: Int): ArrayList<Order>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getOrdersByClient(clientID)
                val response = call?.execute()
                parseAndHandleOrders(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getOrdersByCourier(courierID: Int): ArrayList<Order>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getOrdersByCourier(courierID)
                val response = call?.execute()
                parseAndHandleOrders(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getOrdersByCreatedTime(createdTime: Date): ArrayList<Order>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getOrdersByCreatedTime(createdTime)
                val response = call?.execute()
                parseAndHandleOrders(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getOrdersByCreatedTimePeriod(fromTime: Date, toTime: Date): ArrayList<Order>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getOrdersByCreatedTimePeriod(fromTime, toTime)
                val response = call?.execute()
                parseAndHandleOrders(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getOrdersByDeliveryPrice(deliveryPrice: Float): ArrayList<Order>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getOrdersByDeliveryPrice(deliveryPrice)
                val response = call?.execute()
                parseAndHandleOrders(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getOrdersByDeliveryTime(deliveryTime: Int): ArrayList<Order>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getOrdersByDeliveryTime(deliveryTime)
                val response = call?.execute()
                parseAndHandleOrders(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getOrdersByDoneTime(doneTime: Date): ArrayList<Order>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getOrdersByDoneTime(doneTime)
                val response = call?.execute()
                parseAndHandleOrders(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getOrdersByDoneTimePeriod(fromTime: Date, toTime: Date): ArrayList<Order>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getOrdersByDoneTimePeriod(fromTime, toTime)
                val response = call?.execute()
                parseAndHandleOrders(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getOrdersByPaymentMethod(paymentMethod: String): ArrayList<Order>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getOrdersByPaymentMethod(paymentMethod)
                val response = call?.execute()
                parseAndHandleOrders(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getOrdersByStatus(status: String): ArrayList<Order>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getOrdersByStatus(status)
                val response = call?.execute()
                parseAndHandleOrders(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getOrdersByTotalPrice(totalPrice: Float): ArrayList<Order>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getOrdersByTotalPrice(totalPrice)
                val response = call?.execute()
                parseAndHandleOrders(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getOrdersWithDeliveryPriceBetween(minPrice: Float, maxPrice: Float): ArrayList<Order>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getOrdersWithDeliveryPriceBetween(minPrice, maxPrice)
                val response = call?.execute()
                parseAndHandleOrders(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getOrdersWithDeliveryPriceMoreThan(deliveryPrice: Float): ArrayList<Order>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getOrdersWithDeliveryPriceMoreThan(deliveryPrice)
                val response = call?.execute()
                parseAndHandleOrders(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getOrdersWithDeliveryPriceLessThan(deliveryPrice: Float): ArrayList<Order>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getOrdersWithDeliveryPriceLessThan(deliveryPrice)
                val response = call?.execute()
                parseAndHandleOrders(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getOrdersWithDeliveryTimeBetween(minTime: Int, maxTime: Int): ArrayList<Order>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getOrdersWithDeliveryTimeBetween(minTime, maxTime)
                val response = call?.execute()
                parseAndHandleOrders(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getOrdersWithDeliveryTimeMoreThan(deliveryTime: Int): ArrayList<Order>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getOrdersWithDeliveryTimeMoreThan(deliveryTime)
                val response = call?.execute()
                parseAndHandleOrders(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getOrdersWithDeliveryTimeLessThan(deliveryTime: Int): ArrayList<Order>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getOrdersWithDeliveryTimeLessThan(deliveryTime)
                val response = call?.execute()
                parseAndHandleOrders(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getOrdersWithTotalPriceBetween(minPrice: Float, maxPrice: Float): ArrayList<Order>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getOrdersWithTotalPriceBetween(minPrice, maxPrice)
                val response = call?.execute()
                parseAndHandleOrders(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getOrdersWithTotalPriceMoreThan(totalPrice: Float): ArrayList<Order>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getOrdersWithTotalPriceMoreThan(totalPrice)
                val response = call?.execute()
                parseAndHandleOrders(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getOrdersWithTotalPriceLessThan(totalPrice: Float): ArrayList<Order>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getOrdersWithTotalPriceLessThan(totalPrice)
                val response = call?.execute()
                parseAndHandleOrders(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
}