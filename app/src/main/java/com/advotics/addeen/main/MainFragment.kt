package com.advotics.addeen.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
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
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputLayout
import com.noscale.cerberus.base.BaseFragment
import com.noscale.cerberus.ui.typography.ExtendedTextView
import java.util.*

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
                val tvTitle = appCompatActivity?.findViewById<ExtendedTextView>(R.id.toolbar_title)
                val ivTool = appCompatActivity?.findViewById<AppCompatImageView>(R.id.toolbar_tool)

                var text: String

                when (position) {
                    0 -> text = getString(R.string.tab_user)
                    1 -> {
                        ivTool?.visibility = View.VISIBLE
                        ivTool.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_dots))
                        ivTool.setOnClickListener {
                            getPopup(it)
                        }

                        text = getString(R.string.tab_recipients)
                    }
                    2 -> text = getString(R.string.tab_scan)
                    else -> text = getString(R.string.tab_profile)
                }

                tvTitle.text = text
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

    private fun getFilter (): BottomSheetDialog {
        val dialog = BottomSheetDialog(context!!)
        dialog.setContentView(R.layout.dialog_filter)

        val years = getYears()
        val ilYear = dialog?.findViewById<TextInputLayout>(R.id.il_filter_year)
        val etYear = ilYear?.editText as? AutoCompleteTextView
        val adapter = ArrayAdapter(requireContext(), R.layout.item_list, years)

        etYear?.setAdapter(adapter)
        etYear?.setText(years[years.size - 1].toString(), false)

        return dialog
    }

    @SuppressLint("RestrictedApi")
    private fun getPopup (v: View) {
        val menu = PopupMenu(context, v)
        menu.menuInflater.inflate(R.menu.popup, menu.menu)
        menu?.show()

        menu.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.menu_report -> {
                    return@setOnMenuItemClickListener true
                }
                else -> {
                    getFilter().show()
                    return@setOnMenuItemClickListener true
                }
            }
        }
    }

    private fun getYears (): MutableList<Int> {
        val years = mutableListOf<Int>()
        val current = Calendar.getInstance().get(Calendar.YEAR)

        for (i in 2000..current) {
            years.add(i)
        }

        return years
    }

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }

    class ViewPagerAdapter(activity: FragmentActivity?, private val itemCount: Int): FragmentStateAdapter(activity!!) {
        override fun getItemCount(): Int = itemCount

        private val userFragment = UserFragment.newInstance()

        private val recipientFragment = RecipientFragment.newInstance()

        private val scanFragment = ScanFragment.newInstance()

        private val profileFragment = ProfileFragment.newInstance()

        init {
            UserPresenter(userFragment,true)
            RecipientPresenter(recipientFragment, true)
        }

        override fun createFragment(position: Int): Fragment {


            return when (position) {
                0 -> userFragment
                1 -> recipientFragment
                2 -> scanFragment
                else -> profileFragment
            }
        }
    }
}