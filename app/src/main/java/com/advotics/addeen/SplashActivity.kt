package com.advotics.addeen

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.advotics.addeen.utils.Actions
import com.advotics.addeen.utils.AppConfiguration

class SplashActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            var intent = Actions.openLoginIntent(this)
            val user = AppConfiguration.getInstance(this).mUser

            user?.let {
                intent = Actions.openDashboardIntent(this)
            }

            startActivity(intent)
            finish()
        }, 3000)
    }
}