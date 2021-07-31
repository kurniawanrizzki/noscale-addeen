package com.advotics.addeen.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContextWrapper
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.advotics.addeen.R
import com.advotics.addeen.data.Recipient
import com.advotics.addeen.data.RecipientPackage
import com.advotics.addeen.data.source.RecipientDataSource
import com.advotics.addeen.data.source.remote.recipient.RecipientDataRemoteSource
import com.advotics.addeen.recipient.RecipientFragment
import com.advotics.addeen.recipient.RecipientPresenter
import com.advotics.addeen.scan.ScanFragment
import com.advotics.addeen.user.UserFragment
import com.advotics.addeen.user.UserPresenter
import com.advotics.addeen.utils.ErrorCode
import com.advotics.addeen.utils.pdfcommon.Utility
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputLayout
import com.noscale.cerberus.base.BaseFragment
import com.noscale.cerberus.ui.typography.ExtendedTextView
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.content.FileProvider
import com.advotics.addeen.data.Admin
import com.advotics.addeen.scan.ScanPresenter
import com.advotics.addeen.utils.AppConfiguration
import com.advotics.addeen.utils.AppHelper


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
                val tvTitle = appCompatActivity.findViewById<ExtendedTextView>(R.id.toolbar_title)
                val ivTool = appCompatActivity.findViewById<AppCompatImageView>(R.id.toolbar_tool)
                ivTool.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_dots))
                ivTool.setOnClickListener {
                    getPopup(it)
                }

                var text: String

                when (position) {
                    0 -> {
                        ivTool?.visibility = View.GONE
                        text = getString(R.string.tab_user)
                    }
                    1 -> {
                        ivTool?.visibility = View.VISIBLE
                        text = getString(R.string.tab_recipients)
                    }
                    2 -> {
                        ivTool?.visibility = View.GONE
                        text = getString(R.string.tab_scan)
                    }
                    else -> {
                        ivTool?.visibility = View.GONE
                        text = getString(R.string.tab_profile)
                    }
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

                    if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
                    }
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
        val ilYear = dialog.findViewById<TextInputLayout>(R.id.il_filter_year)
        val bPrint = dialog.findViewById<LinearLayout>(R.id.filter_footer)
        val etYear = ilYear?.editText as? AutoCompleteTextView
        val adapter = ArrayAdapter(requireContext(), R.layout.item_list, years)

        etYear?.setAdapter(adapter)
        etYear?.setText(years[years.size - 1].toString(), false)

        bPrint?.setOnClickListener {
            val year = etYear?.text.toString().toInt()
            RecipientDataRemoteSource.getInstance().getRecipientList(null, null, null, year,  object: RecipientDataSource.RecipientListCallback {
                override fun onLoadCallback(data: List<Recipient>) {
                    dialog.dismiss()

                    if (data.isEmpty()) {
                        Snackbar.make(view!!, ErrorCode.NO_RESULTS.message, Snackbar.LENGTH_LONG).show()
                        return
                    }

                    val fileName = "${SimpleDateFormat("YYYYMMDD").format(Date())}.pdf"
                    val path = generateDownloadedFile(fileName)
                    Utility.createPdf(context!!, object: Utility.OnDocumentClose {
                        override fun onPdfDocumentClose(file: File) {
                            Snackbar.make(view!!, ErrorCode.OK_RESULTS.message, Snackbar.LENGTH_LONG).show()
                        }
                    }, consumeListOfRecipientForDoc(data), path.absolutePath, true)
                }

                override fun onErrorCallback(e: ErrorCode) {
                    Snackbar.make(view!!, e.message, Snackbar.LENGTH_LONG).show()
                    dialog.dismiss()
                }
            })
        }

        return dialog
    }

    private fun consumeListOfRecipientForDoc (items: List<Recipient>): List<Array<String?>> {
        val results: MutableList<Array<String?>> = mutableListOf()

        for (item in items) {
            val c = arrayOf(
                item.name,
                item.email,
                item.gender,
                item.phone,
                item.year,
                readStatusPackage(item.recipientPackage)
            )
            results.add(c)
        }

        return results
    }

    private fun readStatusPackage (r: RecipientPackage?): String? {
        r?.let {
            if (it.qrSentStatus && it.packageReceivedStatus) {
                return getString(R.string.status_received)
            } else if (it.qrSentStatus) {
                return getString(R.string.status_broadcast_message)
            }
        }

        return getString(R.string.status_register)
    }

    @SuppressLint("RestrictedApi")
    private fun getPopup (v: View) {
        val menu = PopupMenu(context, v)
        menu.menuInflater.inflate(R.menu.popup, menu.menu)
        menu.show()

        menu.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.menu_report -> {
                    if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 200)
                        return@setOnMenuItemClickListener true
                    }

                    prepareReport()
                    return@setOnMenuItemClickListener true
                }
                else -> {
                    if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 300)
                        return@setOnMenuItemClickListener true
                    }

                    getFilter().show()
                    return@setOnMenuItemClickListener true
                }
            }
        }
    }

    private fun prepareReport () {
        val year = SimpleDateFormat("YYYY").format(Date()).toInt()
        RecipientDataRemoteSource.getInstance().getRecipientList(null, null, null, year,  object: RecipientDataSource.RecipientListCallback {
            override fun onLoadCallback(data: List<Recipient>) {

                if (data.isEmpty()) {
                    Snackbar.make(view!!, ErrorCode.NO_RESULTS.message, Snackbar.LENGTH_LONG).show()
                    return
                }

                val fileName = "${SimpleDateFormat("YYYYMMDD").format(Date())}.pdf"
                val path = generateDownloadedFile(fileName)
                Utility.createPdf(context!!, object: Utility.OnDocumentClose {
                    override fun onPdfDocumentClose(file: File) {
                        sendReport(fileName)
                        Snackbar.make(view!!, "Preparing...", Snackbar.LENGTH_LONG).show()
                    }
                }, consumeListOfRecipientForDoc(data), path.absolutePath, true)
            }

            override fun onErrorCallback(e: ErrorCode) {
                Snackbar.make(view!!, e.message, Snackbar.LENGTH_LONG).show()
            }
        })
    }

    private fun sendReport (fileName: String) {
        val root = Environment.getExternalStorageDirectory()
        val file = File(root, fileName)

        if (!file.exists() || !file.canRead()) {
            return
        }

        var uri: Uri? = FileProvider.getUriForFile(
            context!!,
            context!!.applicationContext.packageName.toString() + ".provider",
            file
        )

        val user = AppConfiguration.getInstance(context!!).user
        var email: String?

        if (AppConfiguration.getInstance(context!!).isAdmin) {
            email = AppHelper.fromJson<Admin>(user!!).email
        } else {
            email = AppHelper.fromJson<Recipient>(user!!).email
        }

        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "text/plain"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Recipient Data")

        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(emailIntent, "Pick an Email provider"));
    }

    private fun getYears (): MutableList<Int> {
        val years = mutableListOf<Int>()
        val current = Calendar.getInstance().get(Calendar.YEAR)

        for (i in 2000..current) {
            years.add(i)
        }

        return years
    }

    private fun generateDownloadedFile (fileName: String): File {
        val contextWrapper = ContextWrapper(context)
        val downloadedFile = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)

        return File(downloadedFile, fileName)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == 200) {
                prepareReport()
            } else if (requestCode == 300) {
                getFilter().show()
            }
        }

        if ((requestCode == 100) && (grantResults[0] == PackageManager.PERMISSION_DENIED)) {
            Snackbar.make(view!!, "You are not able to scan QR, Please to grant the permission", Snackbar.LENGTH_LONG).show()
        }
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
            ScanPresenter(scanFragment)
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