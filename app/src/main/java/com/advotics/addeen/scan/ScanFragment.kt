package com.advotics.addeen.scan

import android.os.Bundle
import android.view.View
import com.advotics.addeen.R
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.noscale.cerberus.base.BaseFragment

class ScanFragment: BaseFragment() {
    override var mLayoutResource: Int = R.layout.fragment_scan

    private var barcodeView: DecoratedBarcodeView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        barcodeView = view.findViewById(R.id.dbv_scan_view)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        barcodeView?.let {
            if (isVisibleToUser) {
                it.resume()
                return@let
            }

            it.pauseAndWait()
        }
    }

    override fun onPause() {
        super.onPause()
        barcodeView?.pauseAndWait()
    }

    override fun onDestroy() {
        super.onDestroy()
        barcodeView?.resume()
    }

    companion object {
        fun newInstance(): ScanFragment = ScanFragment()
    }
}