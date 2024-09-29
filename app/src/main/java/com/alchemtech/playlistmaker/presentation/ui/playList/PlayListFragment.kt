package com.alchemtech.playlistmaker.presentation.ui.playList

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
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
import com.alchemtech.playlistmaker.presentation.ui.playList.fragments.PlayListActionFragment
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
    private var bottomNavigationView: BottomNavigationView? = null
    private var playListId: Long? = null

    //    private val requester = PermissionRequester.instance()
//    private var nameEditText: EditText? = null
//    private var nameTitle: TextView? = null
//    private var descriptionEditText: EditText? = null
//    private var descriptionTitle: TextView? = null
//    private var createBut: Button? = null
    private var progressBar: ProgressBar? = null

    //    private var uri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPlaylistBinding.inflate(inflater, container, false)
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
//        val navController = navHostFragment.navController
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeRenderState()
        prepareBackBut()
        false.bottomNavigatorVisibility()
        prepareNameText()
        prepareDescriptionText()


        binding?.let {
            bottomSheet = it.bottomSheet
        }
        playListId = arguments?.getLong(PLAY_LIST_TRANSFER_KEY)
        viewModel.getPlayList(playListId)

        prepareBackPress()

        binding?.menu?.setOnClickListener {
            val playListActionFragment = PlayListActionFragment()
            childFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_playlist_bottom, playListActionFragment)
                .commit()
        }
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
                        println( childFragmentManager.fragments)
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