package com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alchemtech.playlistmaker.databinding.FragmentFavoriteTracksBinding
import com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.model.FavoriteTracksViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteTracksFragment:Fragment() {

    private var _binding: FragmentFavoriteTracksBinding? = null
    private val favoriteTracksViewModel: FavoriteTracksViewModel by viewModel()

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteTracksBinding.inflate(layoutInflater)
        return _binding?.root
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }
}