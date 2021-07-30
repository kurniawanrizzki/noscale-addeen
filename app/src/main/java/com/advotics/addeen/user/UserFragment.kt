package com.advotics.addeen.user

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.advotics.addeen.R
import com.advotics.addeen.data.Admin
import com.advotics.addeen.utils.Actions
import com.advotics.addeen.utils.PaginationListener
import com.advotics.addeen.utils.SimpleRecyclerAdapter
import com.advotics.addeen.utils.SimpleScrollRecyclerAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.noscale.cerberus.base.BaseFragment
import com.noscale.cerberus.ui.layouts.ConstraintWithIllustrationLayout
import com.noscale.cerberus.ui.typography.ExtendedTextView

class UserFragment: BaseFragment(), UserContract.View {

    override var mLayoutResource: Int = R.layout.fragment_user

    override var mPresenter: UserContract.Presenter? = null

    private var isPageScrolled = false

    private val mAdapter = SimpleScrollRecyclerAdapter(mutableListOf(), R.layout.item_user, object: SimpleRecyclerAdapter.OnViewHolder<Admin> {
        override fun onBindView(holder: SimpleRecyclerAdapter.SimpleViewHolder?, item: Admin?) {
            val ivProfile = holder?.itemView?.findViewById<AppCompatImageView>(R.id.iv_user_profile)
            val tvName = holder?.itemView?.findViewById<ExtendedTextView>(R.id.tv_user_name)
            val tvRole = holder?.itemView?.findViewById<ExtendedTextView>(R.id.tv_user_description)

            tvName?.text = item?.name
            tvRole?.text = "Admin"
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
            startActivity(intent)
        }

        rvData.adapter = mAdapter
    }

    override fun append(data: List<Admin>) {
        removeLoadingItem()

        val container = view as ConstraintWithIllustrationLayout
        container.setIllustrationVisibility(false)

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
        container.setIllustrationVisibility(false)

        showEmptyPage()
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_LONG).show()
        }

        removeLoadingItem()
    }

    override fun addLoadingItem() {
        isPageScrolled = true
        mAdapter?.addLoading(Admin())
    }

    override fun removeLoadingItem() {
        isPageScrolled = false
        mAdapter?.removeLoading()
    }

    companion object {
        fun newInstance(): UserFragment = UserFragment()
    }

}