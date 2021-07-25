package com.advotics.addeen.main

class MainPresenter (private val mView: MainContract.View?): MainContract.Presenter {

    init {
        mView?.mPresenter = this
    }

    override fun start() {

    }
}