package com.advotics.addeen.details

import android.os.Bundle
import android.view.View
import com.advotics.addeen.R
import com.advotics.addeen.data.Recipient
import com.advotics.addeen.data.RecipientPackage
import com.advotics.addeen.utils.Property
import com.noscale.cerberus.base.BaseFragment
import com.noscale.cerberus.ui.typography.ExtendedTextView

class DetailFragment: BaseFragment(), DetailContract.View {

    override var mPresenter: DetailContract.Presenter? = null

    override var mLayoutResource: Int = R.layout.fragment_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipient = activity?.intent?.getParcelableExtra<Recipient>(Property.RECIPIENT_ARG)

        view?.let{
            it.findViewById<ExtendedTextView>(R.id.tv_detail_name).apply {
                text = recipient?.name
            }

            it.findViewById<ExtendedTextView>(R.id.tv_detail_email).apply {
                text = recipient?.email
            }

            it.findViewById<ExtendedTextView>(R.id.tv_detail_phone).apply {
                text = recipient?.phone
            }

            it.findViewById<ExtendedTextView>(R.id.tv_detail_gender).apply {
                text = if (recipient?.gender == "F") "Female" else "Male"
            }

            it.findViewById<ExtendedTextView>(R.id.tv_detail_status).apply {
                text = readStatusPackage(recipient?.recipientPackage)
            }
        }
    }

    private fun readStatusPackage (r: RecipientPackage?): String? {
        r?.let {
            if (it.qrSentStatus && it.packageReceivedStatus) {
                return getString(R.string.status_received)
            } else if (it.qrSentStatus) {
                return getString(R.string.status_broadcast_message)
            }
        }

        return getString(R.string.status_register)
    }

    companion object {
        fun newInstance(): DetailFragment = DetailFragment()
    }
}