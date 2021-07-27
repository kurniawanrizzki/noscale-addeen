package com.advotics.addeen.main

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.advotics.addeen.R
import com.advotics.addeen.utils.Actions
import com.advotics.addeen.utils.Property
import com.advotics.addeen.utils.SimpleRecyclerAdapter
import com.noscale.cerberus.base.BaseFragment
import com.noscale.cerberus.ui.typography.ExtendedTextView

class ProfileFragment: BaseFragment(), MainContract.View {
    override var mPresenter: MainContract.Presenter? = null

    override var mLayoutResource: Int = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menus = Property.ProfileMenu.values().toMutableList()

        val tvInfo = view.findViewById<ExtendedTextView>(R.id.tv_profile_info)
        val rvMenu = view.findViewById<RecyclerView>(R.id.rv_profile_menu)
        rvMenu.adapter = SimpleRecyclerAdapter(menus, R.layout.item_profile_menu, object: SimpleRecyclerAdapter.OnViewHolder<Property.ProfileMenu> {
            override fun onBindView(
                holder: SimpleRecyclerAdapter.SimpleViewHolder?,
                item: Property.ProfileMenu?
            ) {
                holder?.itemView?.setOnClickListener {
                    val intent = Actions.openProfileMenuIntent(context!!, item?.title!!)
                    startActivity(intent)
                }

                val tvTitle = holder?.itemView?.findViewById<ExtendedTextView>(R.id.tv_menu_title)
                tvTitle?.text = getString(item?.title!!)
            }

        })
    }

    companion object {
        fun newInstance(): ProfileFragment = ProfileFragment()
    }
}