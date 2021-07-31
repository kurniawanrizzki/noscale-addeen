package com.advotics.addeen.other

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import com.advotics.addeen.R
import com.advotics.addeen.main.MainContract
import com.advotics.addeen.utils.Actions
import com.advotics.addeen.utils.AppConfiguration
import com.advotics.addeen.utils.Property
import com.noscale.cerberus.base.BaseActivity
import com.noscale.cerberus.ui.typography.ExtendedTextView

class OtherActivity: BaseActivity<MainContract.View, MainContract.Presenter>() {
    override var mView: MainContract.View? = null

    override var mLayoutResource: Int = R.layout.activity_other

    override var mArguments: Bundle? = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        val title = initView()

        try {
            super.onCreate(savedInstanceState)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }

        val toolbar = findViewById<Toolbar>(R.id.tb_creation)
        val tvTitle = findViewById<ExtendedTextView>(R.id.toolbar_title)
        val ivBack = findViewById<AppCompatImageView>(R.id.toolbar_back)

        tvTitle.text = getString(title)
        ivBack?.visibility = View.VISIBLE
        ivBack?.setOnClickListener { onBackPressed() }

        setSupportActionBar(toolbar)
    }

    private fun initView (): Int {
        val v = intent.getIntExtra(Property.PROFILE_MENU_ARG, R.string.profile_logout)

        when (v) {
            R.string.profile_logout -> {
                val intent = Actions.openLoginIntent(this)
                AppConfiguration.getInstance(this).user = null

                startActivity(intent)
                finish()
                return v
            }
            R.string.profile_about_us -> mArguments?.putInt(Property.PROFILE_MENU_RES_ARG, R.layout.fragment_about_us)
            R.string.profile_activities -> mArguments?.putInt(Property.PROFILE_MENU_RES_ARG, R.layout.fragment_activities)
        }

        mView = OtherFragment.newInstance(mArguments)
        return v
    }
}