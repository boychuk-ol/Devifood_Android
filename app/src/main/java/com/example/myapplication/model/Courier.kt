package com.example.myapplication.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class Courier (
    @JsonProperty("courier_id")
    @SerializedName("courier_id")
    val courierID: Int,
    @JsonProperty("full_name")
    @SerializedName("full_name")
    val fullName: String,
    @JsonProperty("phone")
    @SerializedName("phone")
    val phone: String,
    @JsonProperty("email")
    @SerializedName("email")
    val email: String,
    @JsonProperty("work_region")
    @SerializedName("work_region")
    val workRegion: String,
    @JsonProperty("work_area")
    @SerializedName("work_area")
    val workArea: String
)

data class CouriersResponse(
    @JsonProperty("error")
    @SerializedName("error")
    val error: Boolean,
    @JsonProperty("message")
    @SerializedName("message")
    val message: String,
    @JsonProperty("data")
    @SerializedName("data")
    val data: List<Courier>
)

data class CourierResponse(
    @JsonProperty("error")
    @SerializedName("error")
    val error: Boolean,
    @JsonProperty("message")
    @SerializedName("message")
    val message: String,
    @JsonProperty("data")
    @SerializedName("data")
    val data: Courier
)