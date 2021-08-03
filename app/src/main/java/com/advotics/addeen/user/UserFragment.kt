package com.advotics.addeen.user

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.advotics.addeen.R
import com.advotics.addeen.data.Admin
import com.advotics.addeen.utils.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.noscale.cerberus.base.BaseFragment
import com.noscale.cerberus.ui.layouts.ConstraintWithIllustrationLayout
import com.noscale.cerberus.ui.typography.ExtendedTextView
import com.noscale.cerberus.ui.widgets.IllustrationView

class UserFragment: BaseFragment(), UserContract.View {

    override var mLayoutResource: Int = R.layout.fragment_user

    override var mPresenter: UserContract.Presenter? = null

    private var isPageScrolled = false

    private val mAdapter = SimpleScrollRecyclerAdapter(mutableListOf(), R.layout.item_user, object: SimpleRecyclerAdapter.OnViewHolder<Admin> {
        override fun onBindView(holder: SimpleRecyclerAdapter.SimpleViewHolder?, item: Admin?) {
            val ivProfile = holder?.itemView?.findViewById<AppCompatImageView>(R.id.iv_user_profile)
            val ivDelete = holder?.itemView?.findViewById<AppCompatImageView>(R.id.iv_user_delete)
            val tvName = holder?.itemView?.findViewById<ExtendedTextView>(R.id.tv_user_name)
            val tvRole = holder?.itemView?.findViewById<ExtendedTextView>(R.id.tv_user_description)

            tvName?.text = item?.name
            tvRole?.text = "Admin"
            ivDelete?.visibility = View.VISIBLE

            ivDelete?.setOnClickListener {
                MaterialAlertDialogBuilder(context!!)
                    .setTitle(getString(R.string.deletion_title))
                    .setMessage(String.format(getString(R.string.deletion_message), item?.name))
                    .setNegativeButton(getString(R.string.dialog_decline)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton(getString(R.string.dialog_accept)) { dialog, _ ->
                        showProgress()
                        mPresenter?.delete(item!!)

                        dialog.dismiss()
                    }.show()
            }
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvData = view.findViewById<RecyclerView>(R.id.rv_user_data)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab_user_create)

        rvData?.setOnScrollListener(object: PaginationListener(rvData.layoutManager as LinearLayoutManager) {

            override fun loadMoreItems() {
                mPresenter?.fetch()
            }

            override fun isLastPage(): Boolean = mPresenter?.isLastPage!!

            override fun isLoading(): Boolean = isPageScrolled
        })

        fab?.setOnClickListener {
            val intent = Actions.openCreationIntent(context!!, true)
            startActivityForResult(intent, 103)
        }

        rvData.adapter = mAdapter
    }

    override fun append(data: MutableList<Admin>?) {
        removeLoadingItem()

        val container = view as ConstraintWithIllustrationLayout
        container.setIllustrationVisibility(false)

        val position = mAdapter.itemCount

        AppConfiguration.getInstance(context!!).excludeUser(data)

        data?.let {
            mAdapter?.mMainData?.addAll(it)
            mAdapter?.notifyItemRangeInserted(position, it.size)
        }
    }

    private fun showEmptyPage () {
        val container = view as ConstraintWithIllustrationLayout

        container.setIllustrationVisibility(true)
        container?.setIllustrationSrc(R.raw.ic_empty)
        container?.setIllustrationTitle(getString(R.string.empty_title))
        container?.setIllustrationDescription(getString(R.string.empty_description))
    }

    private fun showProgress () {
        val container = view as ConstraintWithIllustrationLayout
        val illustrationView = container.findViewById<IllustrationView>(R.id.cwi_illustration_id)

        illustrationView?.mTryAgainButton?.visibility = View.GONE

        container.setIllustrationVisibility(true)
        container?.setIllustrationSrc(R.raw.ic_progress)
        container?.setIllustrationTitle(getString(R.string.progress_title))
        container?.setIllustrationDescription(getString(R.string.progress_description))
    }

    override fun throwError(message: String) {
        throwError(message, true)
    }

    override fun throwError(message: String, deletion: Boolean) {
        val container = view as ConstraintWithIllustrationLayout
        val illustrationView = container.findViewById<IllustrationView>(R.id.cwi_illustration_id)

        container.setIllustrationVisibility(false)
        illustrationView?.mTryAgainButton?.visibility = View.VISIBLE
        illustrationView?.mTryAgainButton?.setOnClickListener {
            showProgress()
            mPresenter?.fetch()
        }

        if (!deletion) showEmptyPage()

        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_LONG).show()
        }

        removeLoadingItem()
    }

    override fun addLoadingItem() {
        isPageScrolled = true
        mAdapter?.addLoading(Admin())

        mAdapter?.notifyDataSetChanged()
    }

    override fun removeLoadingItem() {
        isPageScrolled = false
        mAdapter?.removeLoading()

        mAdapter?.notifyDataSetChanged()
    }

    override fun removeItem(i: Admin) {
        val pos = mAdapter?.mMainData?.indexOf(i)

        val container = view as ConstraintWithIllustrationLayout
        container.setIllustrationVisibility(false)

        mAdapter?.mMainData?.remove(i)
        mAdapter?.notifyItemRemoved(pos!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 103) mPresenter?.reset()
    }

    companion object {
        fun newInstance(): UserFragment = UserFragment()
    }

}