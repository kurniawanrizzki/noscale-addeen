package com.advotics.addeen.main

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.advotics.addeen.R
import com.advotics.addeen.utils.Property
import com.noscale.cerberus.base.BaseActivity

class MainActivity : BaseActivity<MainContract.View, MainContract.Presenter>() {
    override var mIllustrationDescription: Int? = null

    override var mIllustrationSrc: Int? = null

    override var mIllustrationTitle: Int? = null

    override var mView: MainContract.View? = MainFragment.newInstance()

    override var mPresenter: MainContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        loginAsAdmin()

        super.onCreate(savedInstanceState)
        showIllustration = false

        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun loginAsAdmin () {
        val loginAsAdmin = intent.getBooleanExtra(Property.LOGIN_AS_ADMIN_ARG, false)

        if (!loginAsAdmin) {
            mView = ProfileFragment.newInstance()
            mPresenter = MainPresenter(mView)
        }
    }
}