package com.alchemtech.playlistmaker.presentation.ui.main

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.databinding.ActivityStartBinding
import com.alchemtech.playlistmaker.presentation.ui.main.model.StartViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.koin.androidx.viewmodel.ext.android.viewModel

class StartActivity : AppCompatActivity() {
    private var binding: ActivityStartBinding? = null
    private var bottomSheet: LinearLayout? = null
    private var navHostFragment: NavHostFragment? = null
    private var navController: NavController? = null
    private var bottomNavigationView: BottomNavigationView? = null
    private var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModel<StartViewModel>()

        prepareBinding()
        prepareNavHostFragment()
        prepareNavHostController()
        prepareBottomNavView()
        backPressPrepare()
        prepareBottomSheet()
    }

    private fun prepareBottomNavView() {
        binding?.let {
            bottomNavigationView = it.bottomNavigation
            navController?.let {
                bottomNavigationView?.setupWithNavController(it)
                it.navInflater
            }
        }
    }

    private fun prepareNavHostController() {
        navHostFragment?.let {
            navController = it.navController
        }
    }

    private fun prepareNavHostFragment() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
    }

    private fun prepareBinding() {
        binding = ActivityStartBinding.inflate(layoutInflater)
        binding?.let {
            setContentView(it.root)
        }
    }

    private fun backPressPrepare() {
        onBackPressedDispatcher.addCallback(
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (isEnabled) {
                        navController?.navigateUp()
                    } else {
                        onBackPressed()
                    }
                }

            }
        )
    }

    fun bottomNavigationVisibility(isVisibile: Boolean) {
        binding?.bottomNavigation?.isVisible = isVisibile
    }


    private fun prepareBottomSheet() {
        binding?.let {
            bottomSheet = it.standardBottomSheet
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet!!)
                bottomSheetBehavior?.maxHeight = 160
//                bottomSheetBehavior?.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
//                override fun onStateChanged(bottomSheet: View, newState: Int) {
//                    when (newState) {
//                        BottomSheetBehavior.STATE_EXPANDED -> {}
//
//                        else -> {
//                            bottomSheetBehavior?.state =
//                                BottomSheetBehavior.STATE_HIDDEN
//                        }
//                    }
//                }
//
//                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
//            })

        }
    }


    fun bottomSheetShowMessage(message: String) {
        bottomSheet?.let {
            binding?.message?.text = message
            it.isVisible = true
            bottomSheetBehavior?.onLayoutChild(binding!!.root,binding!!.standardBottomSheet,binding?.message?.height!!.toInt())
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }
}