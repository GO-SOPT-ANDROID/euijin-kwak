package org.android.go.sopt.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.android.go.sopt.databinding.ActivityLoginBinding
import org.android.go.sopt.extension.showToast
import org.android.go.sopt.presentation.UIState
import org.android.go.sopt.presentation.main.MainActivity
import org.android.go.sopt.presentation.sign.SignUpActivity

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initObserve()
    }

    private fun initObserve() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.loginStateFlow.collectLatest {
                    when (it) {
                        is UIState.UnInitialized -> {
                            initViews()
                        }

                        is UIState.Loading -> {
                        }

                        is UIState.Success -> {
                            showToast("로그인 성공")
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        }

                        else -> {
                            showToast("로그인 실패")
                        }
                    }
                }
            }
        }
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