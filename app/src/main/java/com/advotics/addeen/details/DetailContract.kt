package com.advotics.addeen.details

import com.noscale.cerberus.base.BasePresenter
import com.noscale.cerberus.base.BaseView

interface DetailContract {
    interface View: BaseView<Presenter> {
        
    }
    
    interface Presenter: BasePresenter {
        
    }
}