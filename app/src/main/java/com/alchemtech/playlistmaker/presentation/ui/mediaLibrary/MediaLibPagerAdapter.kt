package com.alchemtech.playlistmaker.presentation.ui.mediaLibrary

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.fragment.FavoriteTracksFragment
import com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.fragment.PlayListsFragment

class MediaLibPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return MAX_ITEMS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FavoriteTracksFragment()
            else -> PlayListsFragment()
        }
    }

    companion object {
        private const val MAX_ITEMS = 2
    }
}