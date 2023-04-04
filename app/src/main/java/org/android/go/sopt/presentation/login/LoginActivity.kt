package org.android.go.sopt.presentation.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import org.android.go.sopt.util.IntentKey
import org.android.go.sopt.R
import org.android.go.sopt.model.UserData
import org.android.go.sopt.databinding.ActivityLoginBinding
import org.android.go.sopt.presentation.main.MainActivity
import org.android.go.sopt.presentation.signup.SignUpActivity
import org.android.go.sopt.presentation.base.BaseActivity
import org.android.go.sopt.showSnack

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private var userData: UserData? = null

    private val signUpLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                userData = if (Build.VERSION.SDK_INT >= 33) {
                    result.data?.getParcelableExtra(IntentKey.USER_DATA, UserData::class.java)
                } else {
                    result.data?.getParcelableExtra(IntentKey.USER_DATA)
                }
                binding.root.showSnack(getString(R.string.sign_up_complete))
            }
        }

    override fun setBinding(layoutInflater: LayoutInflater): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        btLogin.setOnClickListener {
            userData?.let { userData ->
                if ((etId.text.toString() == userData.id) && (etPassword.text.toString() == userData.password)) {
                    Intent(this@LoginActivity, MainActivity::class.java).apply {
                        putExtra(IntentKey.USER_DATA, userData)
                    }.let(::startActivity)
                }
            } ?: root.showSnack(getString(R.string.sign_up_not_complete_message))
        }

        btSignUp.setOnClickListener {
            Intent(this@LoginActivity, SignUpActivity::class.java).let {
                signUpLauncher.launch(it)
            }
        }
    }
}