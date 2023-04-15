package org.android.go.sopt.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivityMainBinding
import org.android.go.sopt.presentation.base.BaseViewBindingActivity
import org.android.go.sopt.presentation.main.gallery.GalleryFragment
import org.android.go.sopt.presentation.main.home.HomeFragment
import org.android.go.sopt.presentation.main.search.SearchFragment

class MainActivity : BaseViewBindingActivity<ActivityMainBinding>() {
    override fun setBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFragment()
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        binding.bottomNavigationView.apply {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.menuHome -> {
                        supportFragmentManager.beginTransaction().replace(binding.fragmentContainerView.id, HomeFragment()).commit()
                        true
                    }
                    R.id.menuSearch -> {
                        supportFragmentManager.beginTransaction().replace(binding.fragmentContainerView.id, SearchFragment()).commit()
                        true
                    }
                    R.id.menuGallery -> {
                        supportFragmentManager.beginTransaction().replace(binding.fragmentContainerView.id, GalleryFragment()).commit()
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction().replace(binding.fragmentContainerView.id, HomeFragment()).commit()
    }

    interface OnReselectListener {
        fun onReselect()
    }
}