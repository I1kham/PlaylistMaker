package com.alchemtech.playlistmaker.presentation.ui.playList

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.alchemtech.playlistmaker.App.Companion.PLAY_LIST_TRANSFER_KEY
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.databinding.FragmentPlaylistBinding
import com.alchemtech.playlistmaker.presentation.ui.convertDurationPlurals
import com.alchemtech.playlistmaker.presentation.ui.convertListPlurals
import com.alchemtech.playlistmaker.presentation.ui.dpToPx
import com.alchemtech.playlistmaker.presentation.ui.durationFormatter
import com.alchemtech.playlistmaker.presentation.ui.fillByUriOrPlaceHolderNoCorners
import com.alchemtech.playlistmaker.presentation.ui.main.StartActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlayListFragment : Fragment() {
    private val viewModel: PlayListViewModel by viewModel()
    private var binding: FragmentPlaylistBinding? = null
    private var bottomSheet: LinearLayout? = null
    private var navHostFragment: NavHostFragment? = null
    private var navController: NavController? = null
    private var playListId: Long? = null
    private var plDurationText: TextView? = null
    private var plCount: TextView? = null
    private var plName: TextView? = null
    private var plDescription: TextView? = null
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
        prepareNavController()
        prepareBottomSheet()
        getPlayListId()
        setPlayListIdToViewModel()
        prepareMenuButton()
        prepareTextViews()
    }

    private fun prepareTextViews() {
        plDurationText = binding?.plDuration
        plCount = binding?.tracksCount
        plName = binding?.playListName
        plDescription = binding?.playListDescription
    }

    private fun prepareMenuButton() {
        binding?.menu?.setOnClickListener {
            navController?.navigate(
                R.id.action_tracksRecycleFragment_to_playListActionFragment,
            )
        }
    }

    private fun setPlayListIdToViewModel() {
        viewModel.getPlayList(playListId)
    }

    private fun getPlayListId() {
        playListId = arguments?.getLong(PLAY_LIST_TRANSFER_KEY)
    }

    private fun prepareBottomSheet() {
        binding?.let {
            bottomSheet = it.bottomSheet
            val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_HIDDEN -> {
                            setBlackoutOverlayVisibility(false)
                            navController?.popBackStack()
                        }

                        BottomSheetBehavior.STATE_COLLAPSED -> {
                        }

                        BottomSheetBehavior.STATE_DRAGGING -> {
                        }

                        BottomSheetBehavior.STATE_EXPANDED -> {
                        }

                        BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        }

                        BottomSheetBehavior.STATE_SETTLING -> {
                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    // Do something for slide offset.
                }
            }
            BottomSheetBehavior.from(bottomSheet!!).addBottomSheetCallback(bottomSheetCallback)
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

    private fun observeRenderState() {
        viewModel.observeRenderState().observe(getViewLifecycleOwner()) {
            render(it)
        }
    }

    private fun render(state: PlayListFragmentState) {
        when (state) {
            is PlayListFragmentState.Content -> {
                setPicture(state.playListCover)
                plCount?.text = state.count.convertListPlurals(requireContext())
                plDurationText?.text = state.duration.durationFormatter().toInt()
                    .convertDurationPlurals(requireContext())
                plName?.text = state.name
                plDescription?.text = state.description
            }

            is PlayListFragmentState.Deleted -> showBottomMessage(
                getString(
                    R.string.play_list_del_message,
                    state.message
                )
            )

            PlayListFragmentState.Exit -> findNavController().popBackStack()
            else -> {}
        }
    }

    private fun setPicture(uri: Uri?) {
        binding?.pic?.fillByUriOrPlaceHolderNoCorners(uri, requireContext())
    }


    private fun showBottomMessage(message: String) {
        (activity as StartActivity).bottomSheetShowMessage(message)
    }

    internal fun setBottomTuning(size: Float, hideable: Boolean) {
        BottomSheetBehavior.from(bottomSheet!!).peekHeight = dpToPx(size, requireContext())
        BottomSheetBehavior.from(bottomSheet!!).isHideable = hideable
    }

    internal fun setBlackoutOverlayVisibility(isVisible: Boolean) {
        binding?.let {
            it.overlay.isVisible = isVisible
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    internal fun deletePlaylist() {
        MaterialAlertDialogBuilder(requireContext())
            .setBackground(resources.getDrawable((R.drawable.background)))
            .setTitle(getString(R.string.playList_delete_but))
            .setMessage(
                getString(R.string.del_playlist_message)
            )
            .setNegativeButton(R.string.no) { _, _ ->
            }
            .setPositiveButton(R.string.yes) { _, _ ->
                playListId?.let {
                    viewModel.deletePlayList(it)
                }
            }
            .show()
    }
}