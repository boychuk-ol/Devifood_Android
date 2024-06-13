package com.example.myapplication.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

data class Location (
    @JsonProperty("location_id")
    @SerializedName("location_id")
    val locationID: Int,
    @JsonProperty("full_address")
    @SerializedName("full_address")
    val fullAddress: String,
    @JsonProperty("city")
    @SerializedName("city")
    val city: String,
    @JsonProperty("neighborhood")
    @SerializedName("neighborhood")
    val neighborhood: String?,
    @JsonProperty("street")
    @SerializedName("street")
    val street: String,
    @JsonProperty("street_number")
    @SerializedName("street_number")
    val streetNumber: String?,
    @JsonProperty("coordinates")
    @SerializedName("coordinates")
    val coordinates: LatLng?,
    @JsonProperty("shop")
    @SerializedName("shop")
    val shop: Shop?,
    @JsonProperty("client")
    @SerializedName("client")
    var client: Client?,
)

data class LocationsResponse(
    @JsonProperty("error")
    @SerializedName("error")
    val error: Boolean,
    @JsonProperty("message")
    @SerializedName("message")
    val message: String,
    @JsonProperty("data")
    @SerializedName("data")
    val data: List<Location>
)

data class LocationResponse(
    @JsonProperty("error")
    @SerializedName("error")
    val error: Boolean,
    @JsonProperty("message")
    @SerializedName("message")
    val message: String,
    @JsonProperty("data")
    @SerializedName("data")
    val data: Location
)

data class LocationIdResponse(
    @JsonProperty("error")
    @SerializedName("error")
    val error: Boolean,
    @JsonProperty("message")
    @SerializedName("message")
    val message: String,
    @JsonProperty("location_id")
    @SerializedName("location_id")
    val locationId: Int
)