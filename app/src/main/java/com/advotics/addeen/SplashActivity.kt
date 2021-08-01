package com.advotics.addeen

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.advotics.addeen.utils.Actions
import com.advotics.addeen.utils.AppConfiguration
import com.advotics.addeen.utils.AppHelper
import java.io.File

class SplashActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        AppHelper.createDownloadedDirectory(this)

        Handler().postDelayed({
            var intent = Actions.openLoginIntent(this)
            val user = AppConfiguration.getInstance(this).user

            user?.let {
                intent = Actions.openDashboardIntent(this)
            }

            startActivity(intent)
            finish()
        }, 3000)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == 200) {
                AppHelper.createDownloadedDirectory(this)
            }
        }
    }
}