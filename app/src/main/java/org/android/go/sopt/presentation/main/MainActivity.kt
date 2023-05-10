package org.android.go.sopt.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivityMainBinding
import org.android.go.sopt.presentation.base.BaseViewBindingActivity
import org.android.go.sopt.presentation.main.gallery.GalleryFragment
import org.android.go.sopt.presentation.main.home.HomeFragment
import org.android.go.sopt.presentation.main.player.MusicFragment
import org.android.go.sopt.presentation.main.search.SearchFragment

@AndroidEntryPoint
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
                val fragment = when (it.itemId) {
                    R.id.menuHome -> HomeFragment()
                    R.id.menuSearch -> MusicFragment()
                    R.id.menuGallery -> SearchFragment()
                    R.id.menuPlayer ->  GalleryFragment()
                    else -> null
                }
                fragment?.let { _fragment ->
                    replaceFragment(_fragment)
                    true
                }?: false
            }
            setOnItemReselectedListener {
                when (it.itemId) {
                    R.id.menuHome -> {
                        val currentFragment = supportFragmentManager.fragments.first()
                        if (currentFragment is MusicFragment) {
                            currentFragment.setOnReselectListener().onReselect()
                        }
                    }
                }
            }
        }
    }

    private fun initFragment() {
        replaceFragment(HomeFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(binding.fragmentContainerView.id, fragment)
        }
    }
}