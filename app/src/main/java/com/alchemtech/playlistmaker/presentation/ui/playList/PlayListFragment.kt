package com.alchemtech.playlistmaker.presentation.ui.playList

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.alchemtech.playlistmaker.App.Companion.PLAY_LIST_TRANSFER_KEY
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.databinding.FragmentPlaylistBinding
import com.alchemtech.playlistmaker.presentation.ui.dpToPx
import com.alchemtech.playlistmaker.presentation.ui.fillByUriOrPlaceHolderNoCorners
import com.alchemtech.playlistmaker.presentation.ui.main.StartActivity
import com.alchemtech.playlistmaker.presentation.ui.playList.fragments.TracksRecycleFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlayListFragment : Fragment() {
    private val viewModel: PlayListViewModel by viewModel()
    private var binding: FragmentPlaylistBinding? = null
    private var bottomSheet: LinearLayout? = null
    private var navHostFragment: NavHostFragment? = null
    private var navController: NavController? = null
    private var playListId: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeRenderState()
        prepareBackBut()
        false.bottomNavigatorVisibility()
        prepareNameText()
        prepareDescriptionText()
        prepareNavController()

        binding?.let {
            bottomSheet = it.bottomSheet
        }
        playListId = arguments?.getLong(PLAY_LIST_TRANSFER_KEY)
        viewModel.getPlayList(playListId)


        binding?.menu?.setOnClickListener {
            prepareBackPress()
            navController?.navigate(R.id.action_tracksRecycleFragment_to_playListActionFragment)
        }
    }

    private fun prepareNavController() {
        navHostFragment =
            childFragmentManager.findFragmentById(R.id.fragment_container_playlist_bottom) as NavHostFragment
        navController = navHostFragment?.navController

    }

    override fun onResume() {
        super.onResume()
        false.bottomNavigatorVisibility()
    }

    override fun onDetach() {
        super.onDetach()
        binding = null
    }


    override fun onStop() {
        true.bottomNavigatorVisibility()
        super.onStop()
    }

    private fun Boolean.bottomNavigatorVisibility() {
        (activity as StartActivity).bottomNavigationVisibility(this)
    }

    private fun prepareBackBut() {
        binding?.preview?.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun prepareNameText() {

    }

    private fun prepareDescriptionText() {

    }


    private fun observeRenderState() {
        viewModel.observeRenderState().observe(getViewLifecycleOwner()) {
            render(it)
        }
    }

    private fun render(state: PlayListFragmentState) {
        when (state) {
            is PlayListFragmentState.Content -> {
                setPicture(state.playList.coverUri)
            }

        }
    }

    private fun setPicture(uri: Uri?) {
        binding?.pic?.fillByUriOrPlaceHolderNoCorners(uri, requireContext())
    }

    private fun prepareBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (isEnabled) {
                        this.isEnabled = false
                        println(childFragmentManager.fragments)
                        val tracksRecycleFragment = TracksRecycleFragment()
                        childFragmentManager.beginTransaction()
                            .replace(
                                R.id.fragment_container_playlist_bottom,
                                tracksRecycleFragment
                            )
                            .commit()
                    }
                }
            }
        )
    }

    private fun showBottomMessage(message: String) {
        (activity as StartActivity).bottomSheetShowMessage(message)
    }

    fun setBottomSheetMaxHeight(size: Float) {
        BottomSheetBehavior.from(bottomSheet!!).maxHeight = dpToPx(size, requireContext())

    }
}