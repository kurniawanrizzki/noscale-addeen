package com.advotics.addeen.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.advotics.addeen.R
import com.advotics.addeen.data.Admin
import com.advotics.addeen.data.Recipient
import com.advotics.addeen.utils.*
import com.noscale.cerberus.base.BaseFragment
import com.noscale.cerberus.ui.typography.ExtendedTextView
import com.squareup.picasso.Picasso

class ProfileFragment: BaseFragment(), MainContract.View {
    override var mPresenter: MainContract.Presenter? = null

    override var mLayoutResource: Int = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menus = Property.ProfileMenu.values().toMutableList()

        val ivProfile = view.findViewById<AppCompatImageView>(R.id.iv_profile_photo)
        val tvInfo = view.findViewById<ExtendedTextView>(R.id.tv_profile_info)
        val rvMenu = view.findViewById<RecyclerView>(R.id.rv_profile_menu)

        tvInfo?.let {
            val user = AppConfiguration.getInstance(context!!).user
            val isAdmin = AppConfiguration.getInstance(context!!).isAdmin
            if (isAdmin) {
                val admin = AppHelper.fromJson<Admin>(user!!)
                it.text = admin.name
            } else {
                val recipient = AppHelper.fromJson<Recipient>(user!!)
                it.text = recipient.name
            }
        }

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

        getRecipient()?.let { item ->
            Picasso.get()
                .load(item.photo)
                .placeholder(ContextCompat.getDrawable(context!!, R.drawable.ic_user_profile)!!)
                .into(ivProfile)
        }
    }

    private fun getRecipient(): Recipient? {
        val user = AppConfiguration.getInstance(context!!).user
        if (user?.contains("recipient")!!) return AppHelper.fromJson(user)

        return null
    }

    companion object {
        fun newInstance(): ProfileFragment = ProfileFragment()
    }
}