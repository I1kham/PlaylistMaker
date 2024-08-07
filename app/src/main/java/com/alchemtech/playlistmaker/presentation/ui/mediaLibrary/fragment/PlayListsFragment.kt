package com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alchemtech.playlistmaker.databinding.FragmentPlayListsBinding
import com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.model.PlayListsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayListsFragment : Fragment() {

    private var _binding: FragmentPlayListsBinding? = null
    private val favoriteTracksViewModel: PlayListsViewModel by viewModel()

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentPlayListsBinding.inflate(layoutInflater)
        return _binding?.root
    }
}