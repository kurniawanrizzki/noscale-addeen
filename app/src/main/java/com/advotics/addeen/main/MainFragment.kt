package com.advotics.addeen.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.advotics.addeen.R
import com.advotics.addeen.recipient.RecipientFragment
import com.advotics.addeen.recipient.RecipientPresenter
import com.advotics.addeen.scan.ScanFragment
import com.advotics.addeen.user.UserFragment
import com.advotics.addeen.user.UserPresenter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.noscale.cerberus.base.BaseFragment

class MainFragment: BaseFragment(), MainContract.View {
    override var mPresenter: MainContract.Presenter? = null

    override var mLayoutResource: Int = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ViewPagerAdapter(activity, 4)
        val viewPager = view.findViewById<ViewPager2>(R.id.vp_main_container)
        val tabLayout = view.findViewById<TabLayout>(R.id.tl_main_navigation)

        viewPager.adapter = adapter

        viewPager?.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val appCompatActivity: AppCompatActivity = activity as AppCompatActivity
                var text: String

                when (position) {
                    0 -> text = getString(R.string.tab_user)
                    1 -> text = getString(R.string.tab_recipients)
                    2 -> text = getString(R.string.tab_scan)
                    else -> text = getString(R.string.tab_profile)
                }

                appCompatActivity?.supportActionBar?.title = text
            }
        })

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.tab_user)
                    tab.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_user)
                }
                1 -> {
                    tab.text = getString(R.string.tab_recipients)
                    tab.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_recipients)
                }
                2 -> {
                    tab.text = getString(R.string.tab_scan)
                    tab.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_scan)
                }
                else -> {
                    tab.text = getString(R.string.tab_profile)
                    tab.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_profile)
                }
            }
        }.attach()
    }

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }

    class ViewPagerAdapter(activity: FragmentActivity?, private val itemCount: Int): FragmentStateAdapter(activity!!) {
        override fun getItemCount(): Int = itemCount

        override fun createFragment(position: Int): Fragment {
            val userFragment = UserFragment.newInstance()
            UserPresenter(userFragment)

            val recipientFragment = RecipientFragment.newInstance()
            RecipientPresenter(recipientFragment)

            return when (position) {
                0 -> userFragment
                1 -> recipientFragment
                2 -> ScanFragment.newInstance()
                else -> ProfileFragment.newInstance()
            }
        }
    }
}