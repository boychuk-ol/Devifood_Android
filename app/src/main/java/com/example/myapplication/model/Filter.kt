package com.example.myapplication.model

import android.util.ArrayMap

data class Filter (
    val filterID: Int,
    val rating: Float,
    val ratingBetween: Array<Float?> = arrayOfNulls(2),
    val reviews: Int,
    val reviewsBetween: Array<Int?> = arrayOfNulls(2),
    val name: String,
    val category: String
)