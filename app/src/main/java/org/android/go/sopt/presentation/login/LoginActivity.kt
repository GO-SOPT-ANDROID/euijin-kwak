package org.android.go.sopt.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    override fun onResume() {
        super.onResume()
        binding.run {
            etId.setText("")
            etPassword.setText("")
        }
    }

    private fun initObserve() {
        viewModel.loginStateFlow.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).onEach {
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
        }.launchIn(lifecycleScope)
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