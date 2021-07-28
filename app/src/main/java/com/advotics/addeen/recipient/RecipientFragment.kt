package com.advotics.addeen.recipient

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.advotics.addeen.R
import com.advotics.addeen.data.Recipient
import com.advotics.addeen.utils.SimpleRecyclerAdapter
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
            tvRole?.text = "Redeemed"
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvData = view.findViewById<RecyclerView>(R.id.rv_recipient_data)

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
        Snackbar.make(view!!, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showEmptyPage () {
        val container = view as ConstraintWithIllustrationLayout

        container.setIllustrationVisibility(true)
        container?.setIllustrationSrc(R.raw.ic_empty)
        container?.setIllustrationTitle(getString(R.string.empty_title))
        container?.setIllustrationDescription(getString(R.string.empty_description))
    }

    companion object {
        fun newInstance(): RecipientFragment = RecipientFragment()
    }
}