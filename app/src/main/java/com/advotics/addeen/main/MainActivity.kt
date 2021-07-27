package com.advotics.addeen.main

import android.os.Bundle
import com.noscale.cerberus.base.BaseActivity

class MainActivity : BaseActivity<MainContract.View, MainContract.Presenter>() {
    override var mIllustrationDescription: Int? = null

    override var mIllustrationSrc: Int? = null

    override var mIllustrationTitle: Int? = null

    override var mView: MainContract.View? = MainFragment.newInstance()

    override var mPresenter: MainContract.Presenter? = MainPresenter(mView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showIllustration = false
    }
}