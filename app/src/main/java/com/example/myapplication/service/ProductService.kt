package com.example.myapplication.service

import android.util.Log
import com.example.myapplication.`interface`.RetrofitAPI
import com.example.myapplication.model.Order
import com.example.myapplication.model.Product
import com.example.myapplication.model.ProductResponse
import com.example.myapplication.model.ProductsResponse
import com.example.myapplication.`object`.RetrofitClient
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.util.Date

class ProductService {
    private val apiService = RetrofitClient.getApiClient()?.create(RetrofitAPI::class.java)
    private fun parseAndHandleProduct(response: Response<ProductResponse>?): Product? {
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
            objectMapper.readValue(Gson().toJson(responseBody.data), Product::class.java)
        } else {
            Log.e("API_ERROR", "Error: ${responseBody?.message}")
            null
        }
    }
    private fun parseAndHandleProducts(response: Response<ProductsResponse>?): ArrayList<Product>? {
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
                object : TypeReference<ArrayList<Product>>() {}
            )
        } else {
            Log.e("API_ERROR", "Error: ${responseBody?.message}")
            null
        }
    }
    suspend fun getProducts(): ArrayList<Product>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProducts()
                val response = call?.execute()
                parseAndHandleProducts(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getProductById(productID: Int): Product? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProductById(productID)
                val response = call?.execute()
                parseAndHandleProduct(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getProductsByCode(productCode: String): Product? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProductByCode(productCode)
                val response = call?.execute()
                parseAndHandleProduct(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getProductsByCaloriesTotal(caloriesTotal: Int): ArrayList<Product>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProductsByCaloriesTotal(caloriesTotal)
                val response = call?.execute()
                parseAndHandleProducts(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getProductsByCategory(categoryID: Int): ArrayList<Product>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProductsByCategory(categoryID)
                val response = call?.execute()
                parseAndHandleProducts(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getProductsByCountry(country: String): ArrayList<Product>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProductsByCountry(country)
                val response = call?.execute()
                parseAndHandleProducts(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getProductsByOrganic(organic: Boolean): ArrayList<Product>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProductsByOrganic(organic)
                val response = call?.execute()
                parseAndHandleProducts(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getProductsByPrice(price: Float): ArrayList<Product>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProductsByPrice(price)
                val response = call?.execute()
                parseAndHandleProducts(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getProductsByProducer(producer: String): ArrayList<Product>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProductsByProducer(producer)
                val response = call?.execute()
                parseAndHandleProducts(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getProductsByRating(rating: Float): ArrayList<Product>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProductsByRating(rating)
                val response = call?.execute()
                parseAndHandleProducts(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getProductsByShop(shopID: Int): ArrayList<Product>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProductsByShop(shopID)
                val response = call?.execute()
                parseAndHandleProducts(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getProductsBySubcategory(subcategory: String): ArrayList<Product>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProductsBySubcategory(subcategory)
                val response = call?.execute()
                parseAndHandleProducts(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getProductsBySubsubcategory(subsubcategory: String): ArrayList<Product>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProductsBySubsubcategory(subsubcategory)
                val response = call?.execute()
                parseAndHandleProducts(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getProductsByVegan(forVegans: Boolean): ArrayList<Product>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProductsByVegan(forVegans)
                val response = call?.execute()
                parseAndHandleProducts(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getProductsByVegetarian(forVegetarians: Boolean): ArrayList<Product>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProductsByVegetarian(forVegetarians)
                val response = call?.execute()
                parseAndHandleProducts(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getProductsByWeight(weight: Float): ArrayList<Product>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProductsByWeight(weight)
                val response = call?.execute()
                parseAndHandleProducts(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getProductsWithCaloriesTotalBetween(minCalories: Int, maxCalories: Int): ArrayList<Product>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProductsWithCaloriesTotalBetween(minCalories, maxCalories)
                val response = call?.execute()
                parseAndHandleProducts(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getProductsWithCaloriesTotalMoreThan(caloriesTotal: Int): ArrayList<Product>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProductsWithCaloriesTotalMoreThan(caloriesTotal)
                val response = call?.execute()
                parseAndHandleProducts(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getProductsWithCaloriesTotalLessThan(caloriesTotal: Int): ArrayList<Product>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProductsWithCaloriesTotalLessThan(caloriesTotal)
                val response = call?.execute()
                parseAndHandleProducts(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getProductsWithPriceBetween(minPrice: Float, maxPrice: Float): ArrayList<Product>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProductsWithPriceBetween(minPrice, maxPrice)
                val response = call?.execute()
                parseAndHandleProducts(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getProductsWithPriceMoreThan(totalPrice: Float): ArrayList<Product>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProductsWithPriceMoreThan(totalPrice)
                val response = call?.execute()
                parseAndHandleProducts(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getProductsWithPriceLessThan(totalPrice: Float): ArrayList<Product>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProductsWithPriceLessThan(totalPrice)
                val response = call?.execute()
                parseAndHandleProducts(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getProductsWithRatingBetween(minRating: Float, maxRating: Float): ArrayList<Product>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProductsWithRatingBetween(minRating, maxRating)
                val response = call?.execute()
                parseAndHandleProducts(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getProductsWithRatingMoreThan(rating: Float): ArrayList<Product>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProductsWithRatingMoreThan(rating)
                val response = call?.execute()
                parseAndHandleProducts(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getProductsWithRatingLessThan(rating: Float): ArrayList<Product>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProductsWithRatingLessThan(rating)
                val response = call?.execute()
                parseAndHandleProducts(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getProductsWithWeightBetween(minWeight: Float, maxWeight: Float): ArrayList<Product>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProductsWithWeightBetween(minWeight, maxWeight)
                val response = call?.execute()
                parseAndHandleProducts(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getProductsWithWeightMoreThan(weight: Float): ArrayList<Product>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProductsWithWeightMoreThan(weight)
                val response = call?.execute()
                parseAndHandleProducts(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
    suspend fun getProductsWithWeightLessThan(weight: Float): ArrayList<Product>? {
        return withContext(Dispatchers.IO) {
            try {
                val call = apiService?.getProductsWithWeightLessThan(weight)
                val response = call?.execute()
                parseAndHandleProducts(response)
            } catch (e: Exception) {
                Log.e("API_ERROR", "Network error: ${e.message}")
                null
            }
        }
    }
}