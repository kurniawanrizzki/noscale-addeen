package com.advotics.addeen.create

import android.os.Bundle
import com.advotics.addeen.R
import com.noscale.cerberus.base.BaseActivity

class CreationActivity: BaseActivity<CreationContract.View, CreationContract.Presenter>() {
    override var mView: CreationContract.View? = CreationFragment.newInstance()

    override var mPresenter: CreationContract.Presenter? = CreationPresenter(mView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showIllustration = false

        supportActionBar?.title = getString(R.string.login_add)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}