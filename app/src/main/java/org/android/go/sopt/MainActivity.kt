package org.android.go.sopt

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import org.android.go.sopt.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun setBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    private fun initData() {
        val userData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(IntentKey.USER_DATA, UserData::class.java)
        } else {
            intent.getParcelableExtra(IntentKey.USER_DATA)
        }
        bindViews(userData)
    }

    private fun bindViews(userData: UserData?) = with(binding) {
        userData?.run {
            tvUserName.text = if (name.isNotEmpty()) {
                getString(R.string.user_name_format, name)
            } else {
                getString(R.string.user_name_null)
            }

            tvUserSpecialty.text = if (specialty.isNotEmpty()) {
                getString(R.string.user_specialty_format, specialty)
            } else {
                getString(R.string.user_specialty_null)
            }
        }
    }
}