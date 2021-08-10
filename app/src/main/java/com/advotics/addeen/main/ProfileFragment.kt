package com.advotics.addeen.main

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.advotics.addeen.R
import com.advotics.addeen.data.Admin
import com.advotics.addeen.data.RecipientWrapper
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
        val tvAccount = view.findViewById<ExtendedTextView>(R.id.tv_profile_name)
        val rvMenu = view.findViewById<RecyclerView>(R.id.rv_profile_menu)

        tvAccount?.let {
            val user = AppConfiguration.getInstance(context!!).user
            val isAdmin = AppConfiguration.getInstance(context!!).isAdmin
            if (isAdmin) {
                val admin = AppHelper.fromJson<Admin>(user!!)
                it.text = admin.name
            } else {
                val recipient = AppHelper.fromJson<RecipientWrapper>(user!!)
                it.text = recipient?.recipient.name
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
            if (item.recipient.photo?.isNotEmpty()!!) {
                Picasso.get()
                    .load(item.recipient.photo)
                    .placeholder(ContextCompat.getDrawable(context!!, R.drawable.ic_user_profile)!!)
                    .into(ivProfile)
            }

            showStatusInfo(item)
            showTotalInfo(item)
        }
    }

    private fun getRecipient(): RecipientWrapper? {
        val user = AppConfiguration.getInstance(context!!).user
        if (user?.contains("recipient")!!) return AppHelper.fromJson(user)

        return null
    }

    private fun showStatusInfo (recipient: RecipientWrapper) {
        val tvInfo = view?.findViewById<ExtendedTextView>(R.id.tv_profile_info)

        tvInfo?.let {
            it.visibility = View.VISIBLE

            recipient?.recipient.recipientPackage?.let { pkg ->
                if (pkg.qrSentStatus && pkg.packageReceivedStatus) {
                    it.text = getString(R.string.redeem_description)
                } else if (pkg.qrSentStatus) {
                    it.text = getString(R.string.qr_sent_description)
                } else {
                    it.text = getString(R.string.register_description)
                }
            }

            return
        }

        tvInfo?.visibility = View.GONE
    }

    private fun showTotalInfo (recipient: RecipientWrapper) {
        val llTotalInfo = view?.findViewById<LinearLayoutCompat>(R.id.ll_profile_total)
        val btTotalDetail = view?.findViewById<Button>(R.id.bt_profile_detail)

        btTotalDetail?.let {
            it.visibility = View.VISIBLE

            it.setOnClickListener {
                if (llTotalInfo?.visibility == View.VISIBLE) llTotalInfo?.visibility = View.GONE
                else llTotalInfo?.visibility = View.VISIBLE
            }

            val tvRecipientTotal = view?.findViewById<ExtendedTextView>(R.id.tv_profile_recipient_total)
            val tvRedemptionTotal = view?.findViewById<ExtendedTextView>(R.id.tv_profile_redemption_total)
            val tvSentTotal = view?.findViewById<ExtendedTextView>(R.id.tv_profile_redemption_sent)

            tvRecipientTotal?.text = String.format(getString(R.string.total_prefix), recipient.totalRecipient)
            tvRedemptionTotal?.text = String.format(getString(R.string.total_prefix), recipient.totalRedemption)
            tvSentTotal?.text = String.format(getString(R.string.total_prefix), recipient.totalSent)
        }
    }

    companion object {
        fun newInstance(): ProfileFragment = ProfileFragment()
    }
}