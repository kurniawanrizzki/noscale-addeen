package com.advotics.addeen.utils

import android.content.Context
import android.content.SharedPreferences
import com.advotics.addeen.data.Admin

class AppConfiguration (mContext: Context) {

    private val mPreference: SharedPreferences = mContext?.getSharedPreferences(PREFERENCE_NAME_KEY, Context.MODE_PRIVATE)

    private val mPreferenceEditor: SharedPreferences.Editor = mPreference?.edit()

    var user: String?
        set(value) {
            mPreferenceEditor?.putString(USER_KEY, value).commit()
        }
        get() = mPreference?.getString(USER_KEY, null)

    var isAdmin: Boolean = user?.contains("admin") ?: false

    fun excludeUser (data: MutableList<Admin>?) {
        if (null == data) return

        val admin = AppHelper.fromJson<Admin>(user!!)
        val iData = data.iterator()

        while (iData.hasNext()) {
            val item = iData.next()

            if (admin?.name?.equals(item.name, ignoreCase = true)!!
                || item.name?.equals("Admin1", ignoreCase = true)!!) iData?.remove()
        }
    }

    companion object {
        const val PREFERENCE_NAME_KEY = "ADDEEN_APP"

        const val USER_KEY = "USER_KEY"

        @Volatile
        private var instance: AppConfiguration? = null

        fun getInstance (context: Context): AppConfiguration = instance ?: AppConfiguration(context)
    }
}