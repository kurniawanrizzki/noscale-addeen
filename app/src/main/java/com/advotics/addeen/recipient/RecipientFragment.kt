package com.advotics.addeen.recipient

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.advotics.addeen.R
import com.advotics.addeen.data.Recipient
import com.advotics.addeen.data.RecipientPackage
import com.advotics.addeen.utils.Actions
import com.advotics.addeen.utils.PaginationListener
import com.advotics.addeen.utils.SimpleRecyclerAdapter
import com.advotics.addeen.utils.SimpleScrollRecyclerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.noscale.cerberus.base.BaseFragment
import com.noscale.cerberus.ui.layouts.ConstraintWithIllustrationLayout
import com.noscale.cerberus.ui.typography.ExtendedTextView
import com.noscale.cerberus.ui.widgets.IllustrationView
import com.squareup.picasso.Picasso

class RecipientFragment: BaseFragment(), RecipientContract.View {
    override var mPresenter: RecipientContract.Presenter? = null

    override var mLayoutResource: Int = R.layout.fragment_recipient

    private var isPageScrolled = false

    private val mAdapter = SimpleScrollRecyclerAdapter(mutableListOf(), R.layout.item_user, object: SimpleRecyclerAdapter.OnViewHolder<Recipient> {
        override fun onBindView(holder: SimpleRecyclerAdapter.SimpleViewHolder?, item: Recipient?) {
            val ivProfile = holder?.itemView?.findViewById<AppCompatImageView>(R.id.iv_user_profile)
            val tvName = holder?.itemView?.findViewById<ExtendedTextView>(R.id.tv_user_name)
            val tvRole = holder?.itemView?.findViewById<ExtendedTextView>(R.id.tv_user_description)

            holder?.itemView?.setOnClickListener {
                item?.let {
                    val intent = Actions.openRecipientDetailIntent(context!!, it)
                    startActivity(intent)
                }
            }

            tvName?.text = item?.name
            tvRole?.text = "${readStatusPackage(item?.recipientPackage)} - ${item?.year}"
            Picasso.get()
                .load(item?.photo)
                .placeholder(ContextCompat.getDrawable(context!!, R.drawable.ic_user_profile)!!)
                .into(ivProfile)
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvData = view.findViewById<RecyclerView>(R.id.rv_recipient_data)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab_recipient_create)

        rvData?.setOnScrollListener(object: PaginationListener(rvData.layoutManager as LinearLayoutManager) {

            override fun loadMoreItems() {
                mPresenter?.fetch()
            }

            override fun isLastPage(): Boolean = mPresenter?.isLastPage!!

            override fun isLoading(): Boolean = isPageScrolled
        })

        fab?.setOnClickListener {
            val intent = Actions.openCreationIntent(context!!, false)
            startActivityForResult(intent, 103)
        }

        rvData.adapter = mAdapter
    }

    override fun append(data: List<Recipient>) {
        removeLoadingItem()

        val container = view as ConstraintWithIllustrationLayout
        val illustrationView = container.findViewById<IllustrationView>(R.id.cwi_illustration_id)

        container.setIllustrationVisibility(false)
        illustrationView?.mTryAgainButton?.visibility = View.GONE

        val position = mAdapter.itemCount

        mAdapter?.mMainData?.addAll(data)
        mAdapter?.notifyItemRangeInserted(position, data.size)
    }

    private fun showEmptyPage () {
        val container = view as ConstraintWithIllustrationLayout

        container.setIllustrationVisibility(true)
        container?.setIllustrationSrc(R.raw.ic_empty)
        container?.setIllustrationTitle(getString(R.string.empty_title))
        container?.setIllustrationDescription(getString(R.string.empty_description))
    }

    override fun throwError(message: String) {
        val container = view as ConstraintWithIllustrationLayout
        val illustrationView = container.findViewById<IllustrationView>(R.id.cwi_illustration_id)

        container.setIllustrationVisibility(false)
        illustrationView?.mTryAgainButton?.visibility = View.VISIBLE
        illustrationView?.mTryAgainButton?.setOnClickListener { mPresenter?.fetch() }

        showEmptyPage()
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_LONG).show()
        }

        removeLoadingItem()
    }

    override fun addLoadingItem() {
        isPageScrolled = true
        mAdapter?.addLoading(Recipient())
    }

    override fun removeLoadingItem() {
        isPageScrolled = false
        mAdapter?.removeLoading()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 103) mPresenter?.reset()
    }

    companion object {
        fun newInstance(): RecipientFragment = RecipientFragment()
    }
}