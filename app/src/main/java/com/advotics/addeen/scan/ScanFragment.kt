package com.advotics.addeen.scan

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.advotics.addeen.R
import com.advotics.addeen.data.RecipientPackage
import com.advotics.addeen.data.Redemption
import com.advotics.addeen.data.source.remote.recipient.RecipientRedemptionRequest
import com.advotics.addeen.utils.AppHelper
import com.advotics.addeen.utils.ErrorCode
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonSyntaxException
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.noscale.cerberus.base.BaseFragment
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import com.google.zxing.BarcodeFormat
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.BarcodeCallback
import com.noscale.cerberus.ui.widgets.IllustrationView
import java.util.*


class ScanFragment: BaseFragment(), ScanContract.View {
    override var mLayoutResource: Int = R.layout.fragment_scan

    override var mPresenter: ScanContract.Presenter? = null

    private var barcodeView: DecoratedBarcodeView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        barcodeView = view.findViewById(R.id.dbv_scan_view)

        val integrator = IntentIntegrator.forSupportFragment(this)
        val formats: Collection<BarcodeFormat> =
            Arrays.asList(BarcodeFormat.CODE_39) // Set barcode type

        barcodeView?.barcodeView?.decoderFactory = DefaultDecoderFactory(formats)
        barcodeView?.initializeFromIntent(integrator.createScanIntent())
        barcodeView?.decodeContinuous(callback)
    }

    private val callback: BarcodeCallback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            try {
                barcodeView?.pauseAndWait()
                showIllustration(true)

                val request = AppHelper.fromJson<Redemption>(result.text)
                request?.let {
                    if (null != it.email && null != it.phone && null != it.year) {
                        mPresenter?.redeem(
                            it.email,
                            it.phone,
                            it.year
                        )

                        return
                    }
                }
            } catch (e: JsonSyntaxException) {
                e.printStackTrace()
            }

            Handler().postDelayed({
                barcodeView?.resume()
                showIllustration(false)
                Snackbar.make(view!!, ErrorCode.NO_RESULTS.message, Snackbar.LENGTH_LONG).show()
            },1500)
        }

        override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
    }

    private fun showIllustration (show: Boolean) {
        val ivProgress = view?.findViewById<IllustrationView>(R.id.iv_scan_progress)

        if (show) {
            barcodeView?.visibility = View.GONE
            ivProgress?.visibility = View.VISIBLE
            return
        }

        barcodeView?.visibility = View.VISIBLE
        ivProgress?.visibility = View.GONE
    }

    override fun onSuccess(qrSentStatus: Boolean, packageReceivedStatus: Boolean) {
        showIllustration(false)
        Snackbar.make(view!!, readStatusPackage(qrSentStatus, packageReceivedStatus)!!, Snackbar.LENGTH_LONG).show()

        barcodeView?.resume()
    }

    override fun throwError(message: String) {
        showIllustration(false)
        Snackbar.make(view!!, message, Snackbar.LENGTH_LONG).show()

        barcodeView?.resume()
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

    override fun onResume() {
        super.onResume()
        barcodeView?.resume()
    }

    private fun readStatusPackage (qrSentStatus: Boolean, packageReceivedStatus: Boolean): String? {
        if (qrSentStatus && packageReceivedStatus) {
            return getString(R.string.status_received)
        } else if (qrSentStatus) {
            return getString(R.string.status_broadcast_message)
        }

        return getString(R.string.status_register)
    }

    companion object {
        fun newInstance(): ScanFragment = ScanFragment()
    }
}