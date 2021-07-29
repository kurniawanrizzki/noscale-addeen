package com.advotics.addeen.create

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import com.advotics.addeen.R
import com.noscale.cerberus.base.BaseActivity
import com.noscale.cerberus.ui.typography.ExtendedTextView
import com.noscale.cerberus.ui.widgets.IllustrationView

class CreationActivity: BaseActivity<CreationContract.View, CreationContract.Presenter>() {
    override var mView: CreationContract.View? = CreationFragment.newInstance()

    override var mPresenter: CreationContract.Presenter? = CreationPresenter(mView)

    override var mLayoutResource: Int = R.layout.activity_creation

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }

        val toolbar = findViewById<Toolbar>(R.id.tb_creation)
        val tvTitle = findViewById<ExtendedTextView>(R.id.toolbar_title)
        val ivBack = findViewById<AppCompatImageView>(R.id.toolbar_back)

        tvTitle.text = getString(R.string.login_add)
        ivBack?.visibility = View.VISIBLE
        ivBack?.setOnClickListener { onBackPressed() }

        setSupportActionBar(toolbar)
    }
}