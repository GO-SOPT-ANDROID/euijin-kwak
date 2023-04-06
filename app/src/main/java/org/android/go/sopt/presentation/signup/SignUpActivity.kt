package org.android.go.sopt.presentation.signup

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.android.go.sopt.R
import org.android.go.sopt.data.model.toUserData
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.domain.repository.UserEntity
import org.android.go.sopt.extension.hideSoftKeyboard
import org.android.go.sopt.extension.showSnack
import org.android.go.sopt.presentation.base.BaseViewModelActivity
import org.android.go.sopt.presentation.login.LoginActivity
import org.android.go.sopt.util.IntentKey

@AndroidEntryPoint
class SignUpActivity : BaseViewModelActivity<ActivitySignUpBinding, SignUpViewModel>() {

    override val viewModel: SignUpViewModel by viewModels()
    override val errorHandler: (() -> Unit) = {
        binding.root.showSnack(getString(R.string.sign_up_error_message))
    }

    override fun setBinding(layoutInflater: LayoutInflater): ActivitySignUpBinding {
        return ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun initObserve() {
        viewModel.signUpState.observe(this) { state ->
            when (state) {
                is SignUpState.UnInitialized -> {
                    initViews()
                }
                is SignUpState.SuccessSaveUser -> {
                    finish()
                }
                is SignUpState.Error -> {
                    binding.root.showSnack(getString(R.string.sign_up_error_message))
                }
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        currentFocus?.hideSoftKeyboard()
        return super.dispatchTouchEvent(ev)
    }

    private fun initViews() = with(binding) {
        initEditTextError()
        btSignUpComplete.setOnClickListener {
            checkSignData()
        }
    }

    private fun initEditTextError() {
        binding.etId.run {
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val length = s?.length ?: 0
                    error = if (length < 6) {
                        getString(R.string.sign_up_id_error_message)
                    } else {
                        null
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }

        binding.etPassword.run {
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val length = s?.length ?: 0
                    error = if (length < 8) {
                        getString(R.string.sign_up_password_error_message)
                    } else {
                        null
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }

    private fun checkSignData() = with(binding) {
        if (etId.error.isNullOrEmpty() && etPassword.error.isNullOrEmpty() && etId.text.isNotEmpty() && etPassword.text.isNotEmpty()) {
            val userData = UserEntity(
                etId.text.toString(),
                etPassword.text.toString(),
                etName.text.toString(),
                etSpecialty.text.toString()
            )
            viewModel.updateUser(userData)
        } else {
            root.showSnack(getString(R.string.sign_up_error_message))
        }
    }
}