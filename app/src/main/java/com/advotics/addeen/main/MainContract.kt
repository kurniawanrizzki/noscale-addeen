package com.advotics.addeen.main

import com.noscale.cerberus.base.BasePresenter
import com.noscale.cerberus.base.BaseView

interface MainContract {
    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter {
    }
}