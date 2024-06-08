package com.example.myapplication.model

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image (
    @JsonProperty("image_ID")
    @SerializedName("image_ID")
    val imageID: Int,
    @SerializedName("entity_name")
    @JsonProperty("entity_name")
    val entityName: String,
    @JsonProperty("i_name")
    @SerializedName("i_name")
    val name: String,
    @JsonProperty("extension")
    @SerializedName("extension")
    val extension: String,
    @JsonProperty("full_link")
    @SerializedName("full_link")
    val fullLink: String?
): Parcelable

data class ImagesResponse (
    @JsonProperty("error")
    @SerializedName("error")
    val error: Boolean,
    @JsonProperty("message")
    @SerializedName("message")
    val message: String,
    @JsonProperty("data")
    @SerializedName("data")
    val data: List<Image>
)

data class ImageResponse (
    @JsonProperty("error")
    @SerializedName("error")
    val error: Boolean,
    @JsonProperty("message")
    @SerializedName("message")
    val message: String,
    @JsonProperty("data")
    @SerializedName("data")
    val data: Image
)