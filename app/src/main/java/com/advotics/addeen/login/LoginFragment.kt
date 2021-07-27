package com.advotics.addeen.login

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import androidx.core.widget.doOnTextChanged
import com.advotics.addeen.R
import com.advotics.addeen.utils.Actions
import com.advotics.addeen.utils.AppConfiguration
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputLayout
import com.noscale.cerberus.base.BaseFragment

class LoginFragment: BaseFragment(), LoginContract.View {

    override var mPresenter: LoginContract.Presenter? = null

    override var mLayoutResource: Int = R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ilEmail = view.findViewById<TextInputLayout>(R.id.il_login_email)
        val ilPassword = view.findViewById<TextInputLayout>(R.id.il_login_password)
        val smToggle = view.findViewById<SwitchMaterial>(R.id.sm_login_toggle)
        val bLogin = view.findViewById<Button>(R.id.b_login_submit)

        assignTextWatcherToInput(ilEmail)
        assignTextWatcherToInput(ilPassword)
        assignSwitchMaterialEvent(smToggle)

        bLogin.setOnClickListener {
            val isEmailFilled = isEmailValidated(ilEmail)
            val isPasswordFilled = isInputValidated(ilPassword)

            if (isEmailFilled && isPasswordFilled) {
                showIllustration = true

                val email = ilEmail?.editText?.text.toString()
                val password = ilPassword?.editText?.text.toString()

                mPresenter?.login(email, password)
            }
        }
    }

    private fun assignSwitchMaterialEvent (sw: SwitchMaterial?) {
        sw?.setOnCheckedChangeListener { _, isChecked ->
            mPresenter?.loginAsAdmin = isChecked
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

            if (v.id == R.id.il_login_email) isNotValidated = isEmailValidated(v)
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

    override fun goToDashboard(src: String) {
        AppConfiguration.getInstance(context!!).user = src

        val intent = Actions.openDashboardIntent(context!!)
        startActivity(intent)

        activity?.finish()
    }

    override fun throwError(message: String) {
        showIllustration = false
        Snackbar.make(view!!, message, Snackbar.LENGTH_LONG).show()
    }

    companion object {
        fun newInstance(): LoginFragment = LoginFragment()
    }
}