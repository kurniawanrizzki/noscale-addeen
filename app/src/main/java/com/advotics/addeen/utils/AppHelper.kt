package com.advotics.addeen.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Environment
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import com.advotics.addeen.R
import com.google.gson.Gson
import java.io.ByteArrayOutputStream
import java.io.File

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

    fun createDownloadedDirectory (activity: AppCompatActivity) {
        if (ContextCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 200)
            return
        }

        val path = "${Environment.getExternalStorageDirectory()}/${activity.getString(R.string.app_name)}"
        val dir = File(path)

        if (!dir.exists() && !dir.isDirectory)
            dir.mkdir()
    }
}