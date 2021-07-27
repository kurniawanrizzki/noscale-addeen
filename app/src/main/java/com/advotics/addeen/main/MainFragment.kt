package com.advotics.addeen.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.advotics.addeen.R
import com.advotics.addeen.profile.ProfileFragment
import com.advotics.addeen.recipient.RecipientFragment
import com.advotics.addeen.scan.ScanFragment
import com.advotics.addeen.user.UserFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.noscale.cerberus.base.BaseFragment

class MainFragment: BaseFragment(), MainContract.View {
    override var mPresenter: MainContract.Presenter? = null

    override var mLayoutResource: Int = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ViewPagerAdapter(activity)
        val vpContainer = view.findViewById<ViewPager2>(R.id.vp_main_container)
        val tlNavigation = view.findViewById<TabLayout>(R.id.tl_main_navigation)

        vpContainer.adapter = adapter

        TabLayoutMediator(tlNavigation, vpContainer) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.tab_user)
                1 -> tab.text = getString(R.string.tab_recipients)
                2 -> tab.text = getString(R.string.tab_scan)
                else -> tab.text = getString(R.string.tab_profile)
            }
        }.attach()
    }

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }

    class ViewPagerAdapter(activity: FragmentActivity?): FragmentStateAdapter(activity!!) {
        override fun getItemCount(): Int = 4

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return UserFragment.newInstance()
                1 -> return RecipientFragment.newInstance()
                2 -> return ScanFragment.newInstance()
                else -> return ProfileFragment.newInstance()
            }
        }
    }
}