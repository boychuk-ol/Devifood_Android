package com.example.myapplication.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import java.util.Date

data class Order (
    @JsonProperty("order_id")
    @SerializedName("order_id")
    val orderID: Int,
    @JsonProperty("created")
    @SerializedName("created")
    val created: Date,
    @JsonProperty("done")
    @SerializedName("done")
    val done: Date,
    @JsonProperty("o_status")
    @SerializedName("o_status")
    val status: String,
    @JsonProperty("delivery_price")
    @SerializedName("delivery_price")
    val deliveryPrice: Float,
    @JsonProperty("delivery_time")
    @SerializedName("delivery_time")
    val deliveryTime: Int,
    @JsonProperty("total_price")
    @SerializedName("total_price")
    val totalPrice: Float,
    @JsonProperty("payment_method")
    @SerializedName("payment_method")
    val paymentMethod: String,
    @JsonProperty("client")
    @SerializedName("client")
    val client: Client,
    @JsonProperty("courier")
    @SerializedName("courier")
    val courier: Courier,
    @JsonProperty("shop_location")
    @SerializedName("shop_location")
    val shopLocation: Location,
    @JsonProperty("client_location")
    @SerializedName("client_location")
    val clientLocation: Location,
)

data class OrdersResponse(
    @JsonProperty("error")
    @SerializedName("error")
    val error: Boolean,
    @JsonProperty("message")
    @SerializedName("message")
    val message: String,
    @JsonProperty("data")
    @SerializedName("data")
    val data: List<Order>
)

data class OrderResponse(
    @JsonProperty("error")
    @SerializedName("error")
    val error: Boolean,
    @JsonProperty("message")
    @SerializedName("message")
    val message: String,
    @JsonProperty("data")
    @SerializedName("data")
    val data: Order
)
