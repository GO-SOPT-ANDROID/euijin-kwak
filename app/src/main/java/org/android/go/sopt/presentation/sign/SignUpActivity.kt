package org.android.go.sopt.presentation.sign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.android.go.sopt.R
import org.android.go.sopt.data.api.ServicePool
import org.android.go.sopt.data.model.sopt.SoptSignUpRequest
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.showSnack

class SignUpActivity : AppCompatActivity() {

    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        initEditTextError()
    }

    private fun initViews() {
        initSignUpButton()
        initDuplicateIdButton()
    }

    private fun initSignUpButton() {
        with(binding) {
            btSignUpComplete.setOnClickListener {
                if (isValid()) {
                    val id = etId.text.toString()
                    val password = etPassword.text.toString()
                    val name = etName.text.toString()
                    val skill = etSkill.text.toString()
                    startSignUp(id, password, name, skill)
                } else {
                    showSnackErrorSignUp()
                }
            }
        }
    }

    private fun initDuplicateIdButton() {
//        TODO("Not yet implemented")
    }

    private fun startSignUp(id: String, password: String, name: String, skill: String) {
        lifecycleScope.launch {
            val response = ServicePool.signUpService.postSignUp(SoptSignUpRequest(id, password, name, skill))
            if (response.isSuccessful) {
                finish()
            } else {
                binding.root.showSnack(getString(R.string.sign_up_failed_message))
            }
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
            return etId.error.isNullOrEmpty() && etPassword.error.isNullOrEmpty() && etId.text.isNotEmpty() && etPassword.text.isNotEmpty()
        }
    }

    private fun showSnackErrorSignUp() {
        with(binding) {
            when {
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