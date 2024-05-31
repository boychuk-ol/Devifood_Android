package com.example.myapplication.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class Client (
    @JsonProperty("client_id")
    @SerializedName("client_id")
    val clientId: Int,
    @JsonProperty("phone_number")
    @SerializedName("phone_number")
    val phoneNumber: String,
    @JsonProperty("email")
    @SerializedName("email")
    val email: String,
    @JsonProperty("full_name")
    @SerializedName("full_name")
    val fullName: String
)

data class ClientResponse(
    @JsonProperty("error")
    @SerializedName("error")
    val error: Boolean,
    @JsonProperty("message")
    @SerializedName("message")
    val message: String,
    @JsonProperty("data")
    @SerializedName("data")
    val data: List<Client>
)