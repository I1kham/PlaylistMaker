package com.alchemtech.playlistmaker.presentation.ui.playList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.alchemtech.playlistmaker.App.Companion.PLAY_LIST_TRANSFER_KEY
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.databinding.FragmentPlaylistBinding
import com.alchemtech.playlistmaker.presentation.ui.addPlayList.AddPlayListState
import com.alchemtech.playlistmaker.presentation.ui.dpToPx
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
    private var bottomNavigationView: BottomNavigationView? = null

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
        BottomSheetBehavior.from(bottomSheet!!).maxHeight = dpToPx(230f, requireContext())
        prepareNavHostFragment()
        prepareNavHostController()

        val bundle = bundleOf(PLAY_LIST_TRANSFER_KEY to arguments?.getLong(PLAY_LIST_TRANSFER_KEY))

        val recycleFragment = TracksRecycleFragment()
        recycleFragment.arguments = bundle
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_playlist_bottom, recycleFragment)
            .commit()


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

    private fun prepareNavHostFragment() {
//        childFragmentManager.beginTransaction()
//            .add(R.id.fragment_container_playlist_bottom, TracksRecycleFragment())
//            .commit()


//findNavController().navigate(R.id.action_playList_to_addPlayListFragment2)

    }

    private fun prepareNavHostController() {

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
//        binding?.let {
//            nameEditText = it.playListName
//            nameEditText?.isActivated = false
//            nameTitle = it.playListplayListNameTitle
//            nameTitle?.isVisible = false
//            nameEditText?.doOnTextChanged { text, _, _, _ ->
//                viewModel.setName(text.toString())
//                nameEditText?.isActivated = !text.isNullOrEmpty()
//                nameTitle?.isVisible = !text.isNullOrEmpty()
//                createBut?.isEnabled = !text.isNullOrEmpty()
//            }
//        }
    }

    private fun prepareDescriptionText() {
        binding?.let {
//            descriptionEditText = it.playListDescription
//            descriptionEditText?.isActivated = false
//            descriptionTitle = it.playListDescriptionTitle
//            descriptionTitle?.isVisible = false
//            descriptionEditText?.doOnTextChanged { text, _, _, _ ->
//                viewModel.setDescription(text.toString())
//                descriptionEditText?.isActivated = !text.isNullOrEmpty()
//                descriptionTitle?.isVisible = !text.isNullOrEmpty()
//        }
        }
    }


    private fun observeRenderState() {
//        viewModel.observeRenderState().observe(getViewLifecycleOwner()) {
//            render(it)
//        }
    }

    private fun render(state: AddPlayListState) {
//        when (state) {
//            is AddPlayListState.Exit ->
//                findNavController().popBackStack()
//
//            is AddPlayListState.Loading ->
//                progressBar?.isVisible = true
//
//            is AddPlayListState.SetPic ->
//                setPicture(state.uri)
//        }
    }

    private fun showBottomMessage(message: String) {
        (activity as StartActivity).bottomSheetShowMessage(message)
    }
}