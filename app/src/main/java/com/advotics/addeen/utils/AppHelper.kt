package com.advotics.addeen.utils

import android.graphics.Bitmap
import android.util.Base64
import com.google.gson.Gson
import java.io.ByteArrayOutputStream

object AppHelper {
    fun <T> toJson (src: T): String {
        val gson = Gson()
        return gson.toJson(src)
    }

    inline fun <reified T> fromJson (src: String): T {
        val gson = Gson()
        return gson.fromJson(src, T::class.java)
    }

    fun getResizedBitmapInBase64 (image: Bitmap): String {
        val out = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 50, out)

        return Base64.encodeToString(out.toByteArray(), Base64.DEFAULT)
    }
}