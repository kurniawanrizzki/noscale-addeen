package com.advotics.addeen.recipient

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.advotics.addeen.R
import com.advotics.addeen.data.Recipient
import com.advotics.addeen.data.RecipientPackage
import com.advotics.addeen.utils.Actions
import com.advotics.addeen.utils.SimpleRecyclerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.noscale.cerberus.base.BaseFragment
import com.noscale.cerberus.ui.layouts.ConstraintWithIllustrationLayout
import com.noscale.cerberus.ui.typography.ExtendedTextView

class RecipientFragment: BaseFragment(), RecipientContract.View {
    override var mPresenter: RecipientContract.Presenter? = null

    override var mLayoutResource: Int = R.layout.fragment_recipient

    private val mAdapter = SimpleRecyclerAdapter(mutableListOf(), R.layout.item_user, object: SimpleRecyclerAdapter.OnViewHolder<Recipient> {
        override fun onBindView(holder: SimpleRecyclerAdapter.SimpleViewHolder?, item: Recipient?) {
            val ivProfile = holder?.itemView?.findViewById<AppCompatImageView>(R.id.iv_user_profile)
            val tvName = holder?.itemView?.findViewById<ExtendedTextView>(R.id.tv_user_name)
            val tvRole = holder?.itemView?.findViewById<ExtendedTextView>(R.id.tv_user_description)

            tvName?.text = item?.name
            tvRole?.text = readStatusPackage(item?.recipientPackage)
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvData = view.findViewById<RecyclerView>(R.id.rv_recipient_data)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab_recipient_create)

        fab?.setOnClickListener {
            val intent = Actions.openCreationIntent(context!!, false)
            startActivity(intent)
        }

        rvData.adapter = mAdapter
    }

    override fun append(data: List<Recipient>) {
        val container = view as ConstraintWithIllustrationLayout
        container.setIllustrationVisibility(false)

        val position = mAdapter.itemCount

        mAdapter?.mMainData?.addAll(data)
        mAdapter?.notifyItemRangeInserted(position, data.size)
    }

    override fun throwError(message: String) {
        val container = view as ConstraintWithIllustrationLayout
        container.setIllustrationVisibility(false)

        showEmptyPage()

        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun showEmptyPage () {
        val container = view as ConstraintWithIllustrationLayout

        container.setIllustrationVisibility(true)
        container?.setIllustrationSrc(R.raw.ic_empty)
        container?.setIllustrationTitle(getString(R.string.empty_title))
        container?.setIllustrationDescription(getString(R.string.empty_description))
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
        fun newInstance(): RecipientFragment = RecipientFragment()
    }
}