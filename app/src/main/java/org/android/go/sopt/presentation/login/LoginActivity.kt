package org.android.go.sopt.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivityLoginBinding
import org.android.go.sopt.extension.showToast
import org.android.go.sopt.presentation.main.MainActivity
import org.android.go.sopt.presentation.sign.SignUpActivity

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        initObserve()
        initLoginButton()
        initSignUpButton()
    }

    private fun initObserve() {
        viewModel.loginLiveData.observe(this) {
            it?.let {
                showToast(getString(R.string.login_complete))
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            } ?: kotlin.run {
                showToast("로그인 실패")
            }
        }
    }

    private fun initLoginButton() {
        with(binding) {
            btLogin.setOnClickListener {
                val id = etId.text.toString()
                val password = etPassword.text.toString()
                viewModel.login(id, password)
            }
        }
    }

    private fun initSignUpButton() {
        binding.btSingUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}