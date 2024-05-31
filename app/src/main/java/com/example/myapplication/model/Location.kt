package com.example.myapplication.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class Location (
    @JsonProperty("location_ID")
    @SerializedName("location_ID")
    val locationID: Int,
    @JsonProperty("address")
    @SerializedName("address")
    val address: String
)
