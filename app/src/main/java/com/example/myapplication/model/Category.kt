package com.example.myapplication.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class Category (
    @JsonProperty("category_id")
    @SerializedName("category_id")
    val categoryID: Int,
    @JsonProperty("c_name")
    @SerializedName("c_name")
    val name: String,
    @JsonProperty("subcat_name")
    @SerializedName("subcat_name")
    val subCategory: String,
    @JsonProperty("subsubcat_name")
    @SerializedName("subsubcat_name")
    val subSubCategory: String,
    @JsonProperty("entity_name")
    @SerializedName("entity_name")
    val entityName: String,
    @JsonProperty("image")
    @SerializedName("image")
    val image: Image
)

data class CategoryResponse(
    @JsonProperty("error")
    @SerializedName("error")
    val error: Boolean,
    @JsonProperty("message")
    @SerializedName("message")
    val message: String,
    @JsonProperty("data")
    @SerializedName("data")
    val data: Category
)

data class CategoriesResponse(
    @JsonProperty("error")
    @SerializedName("error")
    val error: Boolean,
    @JsonProperty("message")
    @SerializedName("message")
    val message: String,
    @JsonProperty("data")
    @SerializedName("data")
    val data: List<Category>
)