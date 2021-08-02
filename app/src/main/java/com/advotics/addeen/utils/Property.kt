package com.advotics.addeen.utils

import com.advotics.addeen.R

object Property {
    const val USER_ARG = "USER_ARG"
    const val LOGIN_AS_ADMIN_ARG = "LOGIN_AS_ADMIN_ARG"

    const val PROFILE_MENU_ARG = "PROFILE_MENU_ARG"
    const val PROFILE_MENU_RES_ARG = "PROFILE_MENU_RES_ARG"

    const val ADMIN_CREATION_ARG = "ADMIN_CREATION_ARG"

    const val RECIPIENT_ARG = "RECIPIENT_ARG"

    enum class ProfileMenu (val icon: Int, val title: Int) {
        ABOUT_US (R.drawable.ic_profile, R.string.profile_about_us), MAJLIS_ACTIVITIES (R.drawable.ic_profile, R.string.profile_activities), LOG_OUT (R.drawable.ic_profile, R.string.profile_logout)
    }
}