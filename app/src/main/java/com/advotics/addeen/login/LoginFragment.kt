package com.advotics.addeen.login

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import androidx.core.widget.doOnTextChanged
import com.advotics.addeen.R
import com.google.android.material.textfield.TextInputLayout
import com.noscale.cerberus.base.BaseFragment

class LoginFragment: BaseFragment(), LoginContract.View {

    override var mPresenter: LoginContract.Presenter? = null

    override var mLayoutResource: Int = R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ilEmail = view.findViewById<TextInputLayout>(R.id.il_login_email)
        val ilPassword = view.findViewById<TextInputLayout>(R.id.il_login_password)
        val bLogin = view.findViewById<Button>(R.id.b_login_submit)

        assignTextWatcherToInput(ilEmail)
        assignTextWatcherToInput(ilPassword)

        bLogin.setOnClickListener {
            val isEmailFilled = isEmailValidated(ilEmail)
            val isPasswordFilled = isInputValidated(ilPassword)

            if (isEmailFilled && isPasswordFilled) {
                showIllustration = true

                //TODO presenter to login
                // inherit show illustration in fragment
            }
        }
    }

    override fun isInputValidated(v: TextInputLayout): Boolean {
        v.editText?.text?.let {
            if (it.isEmpty()) {
                v.error = String.format(getString(R.string.error_required_input), v.hint)
                return false
            }

            v.error = null
        }

        return true
    }

    override fun assignTextWatcherToInput(v: TextInputLayout) {
        v.editText?.doOnTextChanged { _, _, _, _ ->
            var isNotValidated = true

            if (v.hint == "Email") isNotValidated = isEmailValidated(v)
            if (isNotValidated) isInputValidated(v)
        }
    }

    override fun isEmailValidated(v: TextInputLayout): Boolean {
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

    companion object {
        fun newInstance(): LoginFragment = LoginFragment()
    }
}