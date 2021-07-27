package com.advotics.addeen.other

import android.content.Context
import android.os.Bundle
import com.advotics.addeen.main.MainContract
import com.advotics.addeen.utils.Property
import com.noscale.cerberus.base.BaseFragment

class OtherFragment: BaseFragment(), MainContract.View {
    override var mPresenter: MainContract.Presenter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mLayoutResource = arguments?.getInt(Property.PROFILE_MENU_RES_ARG)!!
    }

    companion object {
        fun newInstance(bundle: Bundle?): OtherFragment = OtherFragment().apply {
            arguments = bundle
        }
    }
}