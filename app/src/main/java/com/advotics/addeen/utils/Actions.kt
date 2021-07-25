package com.advotics.addeen.utils

import android.content.Context
import android.content.Intent

object Actions {
    fun openLoginIntent(context: Context) = internalIntent(context, "com.noscale.addeen.login.open")

    fun openDashboardIntent(context: Context) = internalIntent(context, "com.nosacle.addeen.dashboard.open")

    private fun internalIntent(context: Context, action: String): Intent = Intent(action).setPackage(context.packageName)
}