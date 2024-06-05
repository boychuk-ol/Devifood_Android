package com.example.myapplication.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class Shop (
    @JsonProperty("shop_id")
    @SerializedName("shop_id")
    val shopID: Int,
    @JsonProperty("shop_name")
    @SerializedName("shop_name")
    val shopName: String,
    @JsonProperty("rating")
    @SerializedName("rating")
    val rating: Float,
    @JsonProperty("reviews")
    @SerializedName("reviews")
    val reviews: Int,
    @JsonProperty("image")
    @SerializedName("image")
    val image: Image,
    @JsonProperty("category")
    @SerializedName("category")
    val category: Category
)

data class ShopsResponse(
    @JsonProperty("error")
    @SerializedName("error")
    val error: Boolean,
    @JsonProperty("message")
    @SerializedName("message")
    val message: String,
    @JsonProperty("data")
    @SerializedName("data")
    val data: List<Shop>
)


data class ShopResponse(
    @JsonProperty("error")
    @SerializedName("error")
    val error: Boolean,
    @JsonProperty("message")
    @SerializedName("message")
    val message: String,
    @JsonProperty("data")
    @SerializedName("data")
    val data: Shop
)