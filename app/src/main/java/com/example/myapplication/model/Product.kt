package com.example.myapplication.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Product (
    @JsonProperty("product_id")
    @SerializedName("product_id")
    val productID: Int,
    @JsonProperty("product_code")
    @SerializedName("product_code")
    val productCode: String,
    @JsonProperty("description")
    @SerializedName("description")
    val description: String,
    @JsonProperty("full_title")
    @SerializedName("full_title")
    val fullTitle: String,
    @JsonProperty("short_title")
    @SerializedName("short_title")
    val shortTitle: String?,
    @JsonProperty("price_with_no_discount")
    @SerializedName("price_with_no_discount")
    val priceWithNoDiscount: Float?,
    @JsonProperty("actual_price")
    @SerializedName("actual_price")
    val actualPrice: Float,
    @JsonProperty("fats_per_100g")
    @SerializedName("fats_per_100g")
    val fatsPer100g: Float?,
    @JsonProperty("proteins_per_100g")
    @SerializedName("proteins_per_100g")
    val proteinsPer100g: Float?,
    @JsonProperty("carbohydrates_per_100g")
    @SerializedName("carbohydrates_per_100g")
    val carbohydratesPer100g: Float?,
    @JsonProperty("calories_per_100g")
    @SerializedName("calories_per_100g")
    val caloriesPer100g: Float?,
    @JsonProperty("calories_total")
    @SerializedName("calories_total")
    val caloriesTotal: Int?,
    @JsonProperty("ingridients")
    @SerializedName("ingridients")
    val ingridients: String?,
    @JsonProperty("producer")
    @SerializedName("producer")
    val producer: String?,
    @JsonProperty("country")
    @SerializedName("country")
    val country: String?,
    @JsonProperty("organic")
    @SerializedName("organic")
    val organic: Number?,
    @JsonProperty("for_vegeterians")
    @SerializedName("for_vegeterians")
    val forVegeterians: Number?,
    @JsonProperty("for_vegans")
    @SerializedName("for_vegans")
    val forVegans: Number?,
    @JsonProperty("weight")
    @SerializedName("weight")
    val weight: Float?,
    @JsonProperty("liters")
    @SerializedName("liters")
    val liters: Float?,
    @JsonProperty("numb_of_items")
    @SerializedName("numb_of_items")
    val numberOfItems: Int?,
    @JsonProperty("rating")
    @SerializedName("rating")
    val rating: Float?,
    @JsonProperty("image")
    @SerializedName("image")
    val image: Image,
    @JsonProperty("shop")
    @SerializedName("shop")
    val shop: Shop,
    @JsonProperty("category")
    @SerializedName("category")
    val category: Category,
): Parcelable

data class ProductsResponse(
    @JsonProperty("error")
    @SerializedName("error")
    val error: Boolean,
    @JsonProperty("message")
    @SerializedName("message")
    val message: String,
    @JsonProperty("data")
    @SerializedName("data")
    val data: List<Product>
)

data class ProductResponse(
    @JsonProperty("error")
    @SerializedName("error")
    val error: Boolean,
    @JsonProperty("message")
    @SerializedName("message")
    val message: String,
    @JsonProperty("data")
    @SerializedName("data")
    val data: Product
)