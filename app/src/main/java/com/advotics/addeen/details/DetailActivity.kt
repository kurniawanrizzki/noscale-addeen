package com.advotics.addeen.details

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import com.advotics.addeen.R
import com.noscale.cerberus.base.BaseActivity
import com.noscale.cerberus.ui.typography.ExtendedTextView

class DetailActivity: BaseActivity<DetailContract.View, DetailContract.Presenter>() {

    override var mLayoutResource: Int = R.layout.activity_detail

    override var mView: DetailContract.View? = DetailFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }

        val toolbar = findViewById<Toolbar>(R.id.tb_creation)
        val tvTitle = findViewById<ExtendedTextView>(R.id.toolbar_title)
        val ivBack = findViewById<AppCompatImageView>(R.id.toolbar_back)

        tvTitle.text = getString(R.string.menu_detail)
        ivBack?.visibility = View.VISIBLE
        ivBack?.setOnClickListener { onBackPressed() }

        setSupportActionBar(toolbar)
    }
}