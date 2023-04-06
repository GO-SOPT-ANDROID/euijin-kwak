package org.android.go.sopt.presentation.login

import android.content.Intent
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.android.go.sopt.R
import org.android.go.sopt.data.model.UserData
import org.android.go.sopt.data.model.toUserData
import org.android.go.sopt.databinding.ActivityLoginBinding
import org.android.go.sopt.extension.hideSoftKeyboard
import org.android.go.sopt.extension.showErrorSnack
import org.android.go.sopt.extension.showSnack
import org.android.go.sopt.extension.showToast
import org.android.go.sopt.presentation.base.BaseViewModelActivity
import org.android.go.sopt.presentation.main.MainActivity
import org.android.go.sopt.presentation.signup.SignUpActivity
import org.android.go.sopt.util.IntentKey

@AndroidEntryPoint
class LoginActivity : BaseViewModelActivity<ActivityLoginBinding, LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels()

    override val errorHandler: (() -> Unit)
        get() = { binding.root.showErrorSnack() }

    override fun setBinding(layoutInflater: LayoutInflater): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun initObserve() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collectLatest {
                    when (it) {
                        is LoginState.UnInitialized -> {
                            initViews()
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
        }

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        currentFocus?.hideSoftKeyboard()
        return super.dispatchTouchEvent(ev)
    }

    private fun initViews() = with(binding) {
        btLogin.setOnClickListener {
            viewModel.readUser()
        }

        btSignUp.setOnClickListener {
            Intent(this@LoginActivity, SignUpActivity::class.java).let(::startActivity)
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
        Intent(this@LoginActivity, MainActivity::class.java).apply {
            putExtra(IntentKey.USER_DATA, userData)
        }.let(::startActivity)
        finish()
    }
}