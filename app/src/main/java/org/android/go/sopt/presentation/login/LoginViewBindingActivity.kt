package org.android.go.sopt.presentation.login

import android.content.Intent
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.go.sopt.R
import org.android.go.sopt.data.model.UserData
import org.android.go.sopt.data.model.toUserData
import org.android.go.sopt.databinding.ActivityLoginBinding
import org.android.go.sopt.extension.hideSoftKeyboard
import org.android.go.sopt.extension.showErrorSnack
import org.android.go.sopt.extension.showSnack
import org.android.go.sopt.extension.showToast
import org.android.go.sopt.presentation.base.BaseViewModelViewBindingActivity
import org.android.go.sopt.presentation.main.MainViewBindingActivity
import org.android.go.sopt.presentation.signup.SignUpViewBindingActivity
import org.android.go.sopt.util.IntentKey

@AndroidEntryPoint
class LoginViewBindingActivity : BaseViewModelViewBindingActivity<ActivityLoginBinding, LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels()

    override val errorHandler: (() -> Unit)
        get() = { binding.root.showErrorSnack() }

    override fun setBinding(layoutInflater: LayoutInflater): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun initObserve() {
        viewModel.loginState.observe(this) {
            when (it) {
                is LoginState.UnInitialized -> {
                    viewModel.getAutoLogin()
                }
                is LoginState.SuccessGetAutoLogin -> {
                    if (it.isAutoLogin) {
                        Intent(this, MainViewBindingActivity::class.java).let(::startActivity)
                        finish()
                    } else {
                        initViews()
                    }
                }
                is LoginState.SuccessGetUserData -> {
                    checkLoginData(it.user.toUserData())
                }
                is LoginState.LoginFail -> {
                    binding.root.showSnack(getString(R.string.login_not_complete_message))
                }
                is LoginState.Error -> {
                    binding.root.showErrorSnack()
                }

            }

        }

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        currentFocus?.hideSoftKeyboard()
        return super.dispatchTouchEvent(ev)
    }

    private fun initViews() = with(binding) {
        btLogin.setOnClickListener {
            viewModel.setAutoLogin(cbAutoLogin.isChecked)
            viewModel.readUser()
        }

        btSignUp.setOnClickListener {
            Intent(this@LoginViewBindingActivity, SignUpViewBindingActivity::class.java).let(::startActivity)
        }
    }

    private fun checkLoginData(userData: UserData?) = with(binding) {
        userData?.let { userData ->
            if ((etId.text.toString() == userData.id) && (etPassword.text.toString() == userData.password)) {
                successLogin(userData)
            } else {
                root.showSnack(getString(R.string.login_not_complete_message))
            }
        } ?: root.showSnack(getString(R.string.sign_up_not_complete_message))
    }

    private fun successLogin(userData: UserData) {
        showToast(getString(R.string.login_complete))
        Intent(this@LoginViewBindingActivity, MainViewBindingActivity::class.java).apply {
            putExtra(IntentKey.USER_DATA, userData)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }.let(::startActivity)
    }
}