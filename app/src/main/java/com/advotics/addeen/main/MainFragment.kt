package com.advotics.addeen.main

import com.noscale.cerberus.base.BaseFragment

class MainFragment: BaseFragment(), MainContract.View {
    override var mPresenter: MainContract.Presenter? = null

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }
}