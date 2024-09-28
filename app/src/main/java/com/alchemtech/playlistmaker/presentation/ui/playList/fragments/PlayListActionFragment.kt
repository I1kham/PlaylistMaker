package com.alchemtech.playlistmaker.presentation.ui.playList.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alchemtech.playlistmaker.App.Companion.PLAY_LIST_TRANSFER_KEY
import com.alchemtech.playlistmaker.databinding.FragmentPlayListActionBinding
import com.alchemtech.playlistmaker.domain.entity.PlayList
import com.alchemtech.playlistmaker.presentation.ui.main.StartActivity
import com.alchemtech.playlistmaker.presentation.ui.playLikstBottomCard.PlayListBottomCardAdapter
import com.alchemtech.playlistmaker.presentation.ui.playList.fragments.model.PlayListActionFragmentModel
import com.alchemtech.playlistmaker.presentation.ui.playList.fragments.state.PlayListActionFragmentState
import com.alchemtech.playlistmaker.presentation.ui.track_card.TrackCardAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayListActionFragment : Fragment() {
    private val viewModel: PlayListActionFragmentModel by viewModel()
    private lateinit var trackAdapter: TrackCardAdapter
    private var recyclerView: RecyclerView? = null
    private lateinit var adapter: PlayListBottomCardAdapter

    private var binding: FragmentPlayListActionBinding? = null
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
        binding = FragmentPlayListActionBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        false.bottomNavigatorVisibility()
        observeRenderState()
        prepareRecyclerView()
        val playListId = parentFragment?.arguments?.getLong(PLAY_LIST_TRANSFER_KEY)
        viewModel.getPlayList(playListId)
//        prepareBackPress()
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

    private fun observeRenderState() {
        viewModel.observeRenderState().observe(getViewLifecycleOwner()) {
            render(it)
        }
    }

    private fun render(state: PlayListActionFragmentState) {
        when (state) {
            is PlayListActionFragmentState.Content -> {
                state.playList?.upDateAdapter()
            }

        }
    }

    private fun PlayList.upDateAdapter() {
        binding?.trackCardsRecyclerView?.isVisible = true
        adapter = PlayListBottomCardAdapter(listOf(this))
        recyclerView?.adapter = adapter
    }

    private fun Boolean.bottomNavigatorVisibility() {
        (activity as StartActivity).bottomNavigationVisibility(this)
    }

    private fun prepareRecyclerView() {
        recyclerView = binding?.trackCardsRecyclerView
        recyclerView?.layoutManager = GridLayoutManager(
            view?.context,
            1
        )
    }



    private fun showBottomMessage(message: String) {
        (activity as StartActivity).bottomSheetShowMessage(message)
    }
}