package com.advotics.addeen.utils

import com.google.gson.Gson

object AppHelper {
    fun <T> toJson (src: T): String {
        val gson = Gson()
        return gson.toJson(src)
    }

    inline fun <reified T> fromJson (src: String): T {
        val gson = Gson()
        return gson.fromJson(src, T::class.java)
    }
}