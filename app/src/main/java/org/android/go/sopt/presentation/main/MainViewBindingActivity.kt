package org.android.go.sopt.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivityMainBinding
import org.android.go.sopt.extension.getParcelable
import org.android.go.sopt.model.UserData
import org.android.go.sopt.presentation.base.BaseViewBindingActivity
import org.android.go.sopt.util.IntentKey

class MainViewBindingActivity : BaseViewBindingActivity<ActivityMainBinding>() {
    override fun setBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    private fun initData() {
        val userData = intent.getParcelable(IntentKey.USER_DATA, UserData::class.java)
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