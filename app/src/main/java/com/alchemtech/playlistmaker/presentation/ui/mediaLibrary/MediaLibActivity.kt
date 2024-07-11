package com.alchemtech.playlistmaker.presentation.ui.mediaLibrary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.databinding.ActivityMediaLibraryBinding
import com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.model.MediaLibViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class MediaLibActivity : AppCompatActivity() {

    private val viewModel: MediaLibViewModel by viewModel()
    private lateinit var binding: ActivityMediaLibraryBinding
    private var tabMediator: TabLayoutMediator? = null
    private val tabsTitleResIds = listOf(
        R.string.favorite_tracks_fragment_title,
        R.string.playLists_fragment_title
    )
    private var viewPager: ViewPager2? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMediaLibraryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewPager = binding.fragmentContainer
        val back = binding.pageMediaLibPreview
        back.setOnClickListener {
            finish()
        }
        startTableLayout()
    }

    override fun onDestroy() {
        super.onDestroy()
        tabMediator?.detach()
    }

    private fun startTableLayout() = binding.run {
         viewPager!!.adapter = MediaLibPagerAdapter(supportFragmentManager, lifecycle)
        tabMediator = TabLayoutMediator(tabLayout, viewPager!!) { fragment_title, position ->
            fragment_title.text = getString(tabsTitleResIds[position])
        }
        tabMediator?.attach()
    }
}