package org.android.go.sopt.presentation.sign

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.extension.showToast
import org.android.go.sopt.showSnack

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<SignUpViewModel>()
    private var isDuplicatedId = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initObserve()
    }

    private fun initObserve() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.signUpState.collectLatest {
                    when (it) {
                        is SignUpState.UnInitialized -> {
                            initViews()
                            initEditTextError()
                        }
                        is SignUpState.Loading -> {}
                        is SignUpState.DuplicateId -> {
                            isDuplicatedId = false
                            binding.root.showSnack(getString(R.string.sign_up_duplicate_id_message))
                        }
                        is SignUpState.NonDuplicateId -> {
                            isDuplicatedId = false
                            binding.btDuplicateCheck.isEnabled = false
                            binding.root.showSnack(getString(R.string.sign_up_available_id_message))
                        }
                        is SignUpState.SuccessSignUp -> {
                            showToast(getString(R.string.sign_up_success_message))
                            finish()
                        }
                        is SignUpState.Error -> {
                            binding.root.showSnack(getString(R.string.sign_up_failed_message))
                        }
                    }
                }
            }
        }
    }

    private fun initViews() {
        initSignUpButton()
        initDuplicateIdButton()
        initBackButton()
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
                    val id = etId.text.toString()
                    val password = etPassword.text.toString()
                    val name = etName.text.toString()
                    val skill = etSkill.text.toString()
                    viewModel.signUp(id, password, name, skill)
                } else {
                    showSnackErrorSignUp()
                }
            }
        }
    }

    private fun initDuplicateIdButton() {
        binding.btDuplicateCheck.setOnClickListener {
            val id = binding.etId.text.toString()
            viewModel.checkDuplicatedId(id)
        }
    }

    private fun initEditTextError() {
        setIdEditTextError()
        setPasswordEditTextError()
    }

    private fun setIdEditTextError() {
        binding.etId.run {
            doAfterTextChanged { s ->
                val length = s?.length ?: 0
                error = if (length < 6) {
                    getString(R.string.sign_up_id_error_message)
                } else {
                    null
                }
            }
        }
    }

    private fun setPasswordEditTextError() {
        binding.etPassword.run {
            doAfterTextChanged { s ->
                val length = s?.length ?: 0
                error = if (length < 6) {
                    getString(R.string.sign_up_id_error_message)
                } else {
                    null
                }
            }
        }
    }

    private fun isValid(): Boolean {
        with(binding) {
            return etId.error.isNullOrEmpty()
                    && etPassword.error.isNullOrEmpty()
                    && etId.text.isNotEmpty()
                    && etPassword.text.isNotEmpty()
                    && !isDuplicatedId
        }
    }

    private fun showSnackErrorSignUp() {
        with(binding) {
            when {
                isDuplicatedId -> {
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