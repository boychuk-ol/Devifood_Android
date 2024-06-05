package com.example.myapplication.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class OrderProducts (
    @JsonProperty("order_id")
    @SerializedName("order_id")
    val orderID: Int,
    @JsonProperty("product_id")
    @SerializedName("product_id")
    val productID:Int
)

data class OrderProductsResponse(
    @JsonProperty("error")
    @SerializedName("error")
    val error: Boolean,
    @JsonProperty("message")
    @SerializedName("message")
    val message: String,
    @JsonProperty("data")
    @SerializedName("data")
    val data: List<OrderProducts>
)

data class OrderProductResponse(
    @JsonProperty("error")
    @SerializedName("error")
    val error: Boolean,
    @JsonProperty("message")
    @SerializedName("message")
    val message: String,
    @JsonProperty("data")
    @SerializedName("data")
    val data: OrderProducts
)