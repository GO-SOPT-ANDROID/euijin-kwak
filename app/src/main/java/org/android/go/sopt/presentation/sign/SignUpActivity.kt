package org.android.go.sopt.presentation.sign

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.extension.showSnack
import org.android.go.sopt.extension.showToast

class SignUpActivity : AppCompatActivity() {

    private val binding: ActivitySignUpBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
    }
    private val signUpViewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.viewModel = signUpViewModel
        binding.lifecycleOwner = this
        initViews()
    }

    private fun initViews() {
        initObserve()
        initSignUpButton()
        initDuplicateIdButton()
        initBackButton()
    }

    private fun initObserve() {
        signUpViewModel.signUpLiveData.observe(this) {
            if (it != null) {
                showToast(getString(R.string.sign_up_complete))
                finish()
            } else {
                binding.root.showSnack(getString(R.string.sign_up_failed_message))
            }
        }

        signUpViewModel.isDuplicatedId.observe(this) {
            if (it == true) {
                binding.root.showSnack(getString(R.string.sign_up_duplicate_id_message))
            } else {
                binding.btDuplicateCheck.isEnabled = false
                binding.root.showSnack(getString(R.string.sign_up_available_id_message))
            }
        }
    }

    private fun initBackButton() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun initSignUpButton() {
        with(binding) {
            btSignUpComplete.setOnClickListener {
                if (isValid()) {
                    val id = signUpViewModel.idInput.value
                    val password = signUpViewModel.passwordInput.value
                    val name = etName.text.toString()
                    val skill = etSkill.text.toString()
                    signUpViewModel.signUp(id, password, name, skill)
                } else {
                    showSnackErrorSignUp()
                }
            }
        }
    }

    private fun initDuplicateIdButton() {
        binding.btDuplicateCheck.setOnClickListener {
            signUpViewModel.startDuplicateIdCheck()
        }
    }

    private fun isValid(): Boolean {
        with(binding) {
            return (etId.error.isNullOrEmpty()
                    && etPassword.error.isNullOrEmpty()
                    && etId.text.isNotEmpty()
                    && etPassword.text.isNotEmpty())
                    && !(signUpViewModel.isDuplicatedId.value ?:false)
        }
    }

    private fun showSnackErrorSignUp() {
        with(binding) {
            when {
                signUpViewModel.isDuplicatedId.value ?:false -> {
                    root.showSnack(getString(R.string.action_id_duplicate_check))
                }

                etName.text.isNullOrEmpty() -> {
                    root.showSnack(getString(R.string.sign_up_error_name_message))
                }

                etSkill.text.isNullOrEmpty() -> {
                    root.showSnack(getString(R.string.sign_up_error_skill_message))
                }

                else -> {
                    root.showSnack(getString(R.string.sign_up_error_info_message))
                }
            }
        }
    }
}