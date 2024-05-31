package com.example.myapplication.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class Category (
    @JsonProperty("category_id")
    @SerializedName("category_id")
    val categoryID: Int,
    @JsonProperty("name")
    @SerializedName("name")
    val name: String,
    @JsonProperty("sub_category")
    @SerializedName("sub_category")
    val subCategory: String,
    @JsonProperty("subsubcategory")
    @SerializedName("subsubcategory")
    val subSubCategory: String,
    @JsonProperty("entity_name")
    @SerializedName("entity_name")
    val entityName: String,
    @JsonProperty("image")
    @SerializedName("image")
    val image: Image
)