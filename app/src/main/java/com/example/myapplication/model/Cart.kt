package com.example.myapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cart (
    val cartID: Int,
    val products: ArrayList<Product>
): Parcelable