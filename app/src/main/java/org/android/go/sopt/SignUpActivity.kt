package org.android.go.sopt

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import org.android.go.sopt.databinding.ActivitySignUpBinding

class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {


    override fun setBinding(layoutInflater: LayoutInflater): ActivitySignUpBinding {
        return ActivitySignUpBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
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

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

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

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }

    private fun checkSignData() = with(binding) {
        if (etId.error.isNullOrEmpty() && etPassword.error.isNullOrEmpty()) {
            val userData = UserData(
                etId.text.toString(),
                etPassword.text.toString(),
                etName.text.toString(),
                etSpecialty.text.toString()
            )
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java).apply {
                putExtra(IntentKey.USER_DATA, userData)
            }
            Toast.makeText(
                this@SignUpActivity,
                getString(R.string.sign_up_complete),
                Toast.LENGTH_SHORT
            ).show()
            setResult(RESULT_OK, intent)
            finish()
        } else {
            Snackbar.make(root, getString(R.string.sign_up_error_message), Snackbar.LENGTH_SHORT)
                .show()
        }
    }
}