package com.alchemtech.playlistmaker.presentation.ui.playList.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.alchemtech.playlistmaker.databinding.FragmentTracksRecyclerViewBinding
import com.alchemtech.playlistmaker.util.debounce

class TracksRecycleFragment: Fragment() {
    private var binding: FragmentTracksRecyclerViewBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentTracksRecyclerViewBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        run(
            debounce<Any>(3000,lifecycleScope,false){
                binding?.noData?.isVisible= false
            }
        )
    }
}