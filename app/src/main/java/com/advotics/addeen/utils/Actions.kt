package com.advotics.addeen.utils

import android.content.Context
import android.content.Intent

object Actions {
    fun openLoginIntent(context: Context) = internalIntent(context, "com.noscale.addeen.login.open")

    fun openDashboardIntent(context: Context) = internalIntent(context, "com.noscale.addeen.dashboard.open")
        .putExtra(Property.LOGIN_AS_ADMIN_ARG, AppConfiguration.getInstance(context).isAdmin)

    fun openProfileMenuIntent (context: Context, opt: Int) = internalIntent(context, "com.noscale.addeen.profile.open")
        .putExtra(Property.PROFILE_MENU_ARG, opt)

    private fun internalIntent(context: Context, action: String): Intent = Intent(action).setPackage(context.packageName)
}