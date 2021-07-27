package com.advotics.addeen.utils

import android.content.Context
import android.content.SharedPreferences

class AppConfiguration (mContext: Context) {

    private val mPreference: SharedPreferences = mContext?.getSharedPreferences(PREFERENCE_NAME_KEY, Context.MODE_PRIVATE)

    private val mPreferenceEditor: SharedPreferences.Editor = mPreference?.edit()

    var user: String?
        set(value) {
            mPreferenceEditor?.putString(USER_KEY, value).commit()
        }
        get() = mPreference?.getString(USER_KEY, null)

    var isAdmin: Boolean = user?.contains("admin") ?: false

    companion object {
        const val PREFERENCE_NAME_KEY = "ADDEEN_APP"

        const val USER_KEY = "USER_KEY"

        @Volatile
        private var instance: AppConfiguration? = null

        fun getInstance (context: Context): AppConfiguration = instance ?: AppConfiguration(context)
    }
}