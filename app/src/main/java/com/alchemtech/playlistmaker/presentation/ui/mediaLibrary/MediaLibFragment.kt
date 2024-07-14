package com.alchemtech.playlistmaker.presentation.ui.mediaLibrary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.databinding.FragmentMediaLibraryBinding
import com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.model.MediaLibViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class MediaLibFragment : Fragment() {

    private val viewModel: MediaLibViewModel by viewModel()
    private lateinit var binding: FragmentMediaLibraryBinding
    private var tabMediator: TabLayoutMediator? = null
    private val tabsTitleResIds = listOf(
        R.string.favorite_tracks_fragment_title,
        R.string.playLists_fragment_title
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMediaLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startTableLayout()
    }

    override fun onDestroy() {
        tabMediator?.detach()
        super.onDestroy()
    }

    private fun startTableLayout() = binding.run {
        binding.viewPager.adapter = MediaLibPagerAdapter(childFragmentManager, lifecycle)
        tabMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = getString(tabsTitleResIds[position])
        }
        tabMediator?.attach()
    }
}