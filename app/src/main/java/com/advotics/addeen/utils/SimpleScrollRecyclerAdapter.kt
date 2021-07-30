package com.advotics.addeen.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.advotics.addeen.R

class SimpleScrollRecyclerAdapter<T>(
    mMainData: MutableList<T>?, mLayoutRes: Int, mListener: OnViewHolder<T>
): SimpleRecyclerAdapter<T>(mMainData, mLayoutRes, mListener) {

    private var isLoaderVisible: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        if (viewType == LOADING_VIEW_TYPE) {
            val view: View = LayoutInflater.from(parent.context).inflate(
                R.layout.item_user_placeholder,
                parent,
                false
            )

            return LoadingViewHolder(view)
        }

        return super.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        if (holder is LoadingViewHolder) {
            return
        }

        super.onBindViewHolder(holder, position)
    }

    override fun getItemViewType(position: Int): Int {
        if (isLoaderVisible) {
            return if (position == (mMainData?.size?.minus(1))) LOADING_VIEW_TYPE else DEFAULT_VIEW_TYPE
        }
        return DEFAULT_VIEW_TYPE
    }

    fun addLoading (item: T) {
        isLoaderVisible = true
        addItem(item)
    }

    fun removeLoading () {
        isLoaderVisible = false
        val pos = mMainData?.size?.minus(1)
        val item = mMainData?.get(pos!!)
        mMainData?.remove(item)
    }

    class LoadingViewHolder(itemView: View) : SimpleViewHolder(itemView)

    companion object {
        val DEFAULT_VIEW_TYPE = 0
        val LOADING_VIEW_TYPE = 1
    }
}