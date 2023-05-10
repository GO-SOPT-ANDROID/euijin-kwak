package org.android.go.sopt.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.android.go.sopt.R
import org.android.go.sopt.data.api.ApiFactory
import org.android.go.sopt.data.model.sopt.SoptLoginRequest
import org.android.go.sopt.databinding.ActivityLoginBinding
import org.android.go.sopt.extension.showToast
import org.android.go.sopt.presentation.main.MainActivity
import org.android.go.sopt.presentation.sign.SignUpActivity

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        initLoginButton()
        initSignUpButton()
    }

    private fun initLoginButton() {
        with(binding) {
            btLogin.setOnClickListener {
                val id = etId.text.toString()
                val password = etPassword.text.toString()
                startLogin(id, password)
            }
        }
    }

    private fun initSignUpButton() {
        binding.btSingUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun startLogin(id: String, password: String) {
        lifecycleScope.launch {
            val response = ApiFactory.signUpService.postLogin(SoptLoginRequest(id, password))
            if (response.isSuccessful && response.body()?.status == 200) {
                showToast(getString(R.string.login_complete))
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            } else {
                showToast(response.body()?.message ?:getString(R.string.login_not_complete_message))
            }
        }
    }
}