package com.example.myapplication.`object`

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonHelper {
    fun createGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }
}