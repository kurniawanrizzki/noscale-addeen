package com.advotics.addeen.create

import android.Manifest
import android.R.attr
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.widget.doOnTextChanged
import com.advotics.addeen.R
import com.advotics.addeen.data.Admin
import com.advotics.addeen.utils.AppConfiguration
import com.advotics.addeen.utils.AppHelper
import com.advotics.addeen.utils.Property
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.noscale.cerberus.base.BaseFragment
import com.noscale.cerberus.ui.layouts.ConstraintWithIllustrationLayout
import com.noscale.cerberus.ui.typography.ExtendedTextView
import com.noscale.cerberus.ui.widgets.IllustrationView
import java.util.regex.Pattern


class CreationFragment: BaseFragment(), CreationContract.View {
    override var mPresenter: CreationContract.Presenter? = null

    var capturedPhoto: Bitmap? = null

    var mIllustrationView: IllustrationView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val isAdminCreation = activity?.intent?.getBooleanExtra(Property.ADMIN_CREATION_ARG, false)

        mLayoutResource = if (isAdminCreation!!) R.layout.fragment_admin_creation
        else R.layout.fragment_recipient_creation
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mIllustrationView = activity?.findViewById(R.id.cwi_illustration_id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isAdminCreation = activity?.intent?.getBooleanExtra(Property.ADMIN_CREATION_ARG, false)

        if (isAdminCreation!!) initAdminForm()
        else initRecipientForm()
    }

    private fun initRecipientForm () {
        val gender = resources.getStringArray(R.array.form_gender).toList()
        val adapter = ArrayAdapter(requireContext(), R.layout.item_list, gender)

        val ivCapture = view?.findViewById<AppCompatImageView>(R.id.iv_recipient_photo)
        val ilName = view?.findViewById<TextInputLayout>(R.id.il_recipient_name)
        val ilEmail = view?.findViewById<TextInputLayout>(R.id.il_recipient_email)
        val ilPhone = view?.findViewById<TextInputLayout>(R.id.il_recipient_phone)
        val ilGender = view?.findViewById<TextInputLayout>(R.id.il_recipient_gender)
        val etGender = ilGender?.editText as? AutoCompleteTextView

        etGender?.setAdapter(adapter)
        etGender?.setText(gender[0], false)

        val bSubmit = view?.findViewById<Button>(R.id.b_recipient_submit)
        val bCancel = view?.findViewById<Button>(R.id.b_recipient_cancel)

        assignTextWatcherToInput(ilName!!)
        assignTextWatcherToInput(ilEmail!!)
        assignTextWatcherToInput(ilPhone!!)

        ivCapture?.setOnClickListener {
            accessCamera()
        }

        bSubmit?.setOnClickListener {
            val isNameFilled = isInputValidated(ilName)
            val isEmailFilled = isEmailValidated(ilEmail)
            val isPhoneFilled = isPhoneNumberValidated(ilPhone)
            val isTakeSelfie = isSelfieValidated()

            if (isNameFilled && isEmailFilled && isPhoneFilled && isTakeSelfie) {
                mIllustrationView?.visibility = View.VISIBLE
                view?.visibility = View.GONE

                val capturedPhotoInBase64 = AppHelper.getResizedBitmapInBase64(capturedPhoto!!)
                val user = AppHelper.fromJson<Admin>(AppConfiguration.getInstance(context!!).user!!)
                val nameInput = ilName?.editText?.text.toString()
                val emailInput = ilEmail?.editText?.text.toString()
                val phoneInput = ilPhone?.editText?.text.toString()
                var genderInput = ilGender?.editText?.text.toString()
                genderInput = if (genderInput.equals(gender[0])) "M"
                else "F"

                mPresenter?.submitRecipientRegis(
                    user.id, capturedPhotoInBase64, nameInput, emailInput, phoneInput, genderInput
                )
            }
        }

        bCancel?.setOnClickListener { activity?.onBackPressed() }
    }

    private fun initAdminForm () {
        val ilName = view?.findViewById<TextInputLayout>(R.id.il_admin_name)
        val ilEmail = view?.findViewById<TextInputLayout>(R.id.il_admin_email)
        val ilPassword = view?.findViewById<TextInputLayout>(R.id.il_admin_password)
        val bSubmit = view?.findViewById<Button>(R.id.b_admin_submit)
        val bCancel = view?.findViewById<Button>(R.id.b_admin_cancel)

        assignTextWatcherToInput(ilName!!)
        assignTextWatcherToInput(ilEmail!!)
        assignTextWatcherToInput(ilPassword!!)

        bSubmit?.setOnClickListener {
            val isNameFilled = isInputValidated(ilName)
            val isEmailFilled = isEmailValidated(ilEmail)
            val isPasswordFilled = isInputValidated(ilPassword)

            if (isNameFilled && isEmailFilled && isPasswordFilled) {
                mIllustrationView?.visibility = View.VISIBLE
                view?.visibility = View.GONE

                val name = ilName?.editText?.text.toString()
                val email = ilEmail?.editText?.text.toString()
                val password = ilPassword?.editText?.text.toString()

                mPresenter?.submitAdminRegis(name, email, password)
            }
        }

        bCancel?.setOnClickListener { activity?.onBackPressed() }
    }

    override fun onCreationSuccess() {
        mIllustrationView?.visibility = View.GONE
        view?.visibility = View.VISIBLE

        activity?.finish()
    }

    override fun throwError(message: String) {
        mIllustrationView?.visibility = View.GONE
        view?.visibility = View.VISIBLE

        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun isInputValidated(v: TextInputLayout): Boolean {
        v.editText?.text?.let {
            if (it.isEmpty()) {
                v.error = String.format(getString(R.string.error_required_input), v.hint)
                return false
            }

            v.error = null
        }

        return true
    }

    private fun assignTextWatcherToInput(v: TextInputLayout) {
        v.editText?.doOnTextChanged { _, _, _, _ ->
            var isNotValidated = true

            if ((v.id == R.id.il_admin_email) || (v.id == R.id.il_recipient_email)) isNotValidated = isEmailValidated(v)
            if (v.id == R.id.il_recipient_phone) isNotValidated = isPhoneNumberValidated(v)
            if (isNotValidated) isInputValidated(v)
        }
    }

    private fun isEmailValidated(v: TextInputLayout): Boolean {
        v.editText?.text?.let {
            val isEmailValidated = Patterns.EMAIL_ADDRESS.matcher(it).matches()
            if (it.isNotEmpty() && !isEmailValidated) {
                v.error = getString(R.string.error_email_format)
                return false;
            }

            v.error = null
            isInputValidated(v)
        }

        return true
    }

    private fun isSelfieValidated (): Boolean {
        val tvError = view?.findViewById<ExtendedTextView>(R.id.tv_recipient_null_photo)
        if (null != capturedPhoto) {
            tvError?.visibility = View.GONE
            return true
        }

        tvError?.visibility = View.VISIBLE
        return false
    }

    private fun isPhoneNumberValidated (v: TextInputLayout): Boolean {
        v.editText?.text?.let {
            val pattern = Pattern.compile("62\\d{9,11}")
            val matcher = pattern.matcher(it)

            if (!matcher.matches()) {
                v.error = getString(R.string.error_phone_format)
                return false
            }

            v.error = null
            isInputValidated(v)
        }

        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if ((requestCode == 100) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            accessCamera()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ((requestCode == 101) && (resultCode == Activity.RESULT_OK)) {
            capturedPhoto = data?.getExtras()?.get("data") as Bitmap
            val ivCapture = view?.findViewById<AppCompatImageView>(R.id.iv_recipient_photo)

            ivCapture?.setImageBitmap(capturedPhoto)
            isSelfieValidated()
        }
    }

    private fun accessCamera () {
        if (checkSelfPermission(context!!, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            return
        }

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, 101)
    }

    companion object {
        fun newInstance(): CreationFragment = CreationFragment()
    }
}