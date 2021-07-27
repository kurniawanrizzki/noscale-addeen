package com.advotics.addeen.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class SimpleRecyclerAdapter<T>(
    var mMainData: MutableList<T>?,
    private val mLayoutRes: Int,
    private val mListener: OnViewHolder<T>
) : RecyclerView.Adapter<SimpleRecyclerAdapter.SimpleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            mLayoutRes,
            parent,
            false
        )
        return SimpleViewHolder(view)
    }

    override fun getItemCount(): Int = mMainData?.size ?: 0

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        val item = mMainData?.get(position)
        mListener.onBindView(holder, item)
    }

    fun addItemAt(index: Int, item: T) {
        mMainData?.add(index, item)
    }

    fun addItem(item: T) {
        mMainData?.add(item)
    }

    class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnViewHolder<T> {
        fun onBindView(holder: SimpleViewHolder?, item: T?)
    }
}