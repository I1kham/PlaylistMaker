package com.alchemtech.playlistmaker.presentation.ui.playList.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.alchemtech.playlistmaker.databinding.FragmentPlayListActionBinding
import com.alchemtech.playlistmaker.presentation.ui.main.StartActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class PlayListActionFragment:Fragment() {
    //private val viewModel: PlayListViewModel by viewModel()
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

            //  BottomSheetBehavior.from(bottomSheet!!).maxHeight = dpToPx(230f, requireContext())
            prepareNavHostFragment()
            prepareNavHostController()


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

//findNavController().navigate(R.id.action_playList_to_addPlayListFragment2)

        }
        private fun prepareNavHostController() {

        }

        private fun Boolean.bottomNavigatorVisibility() {
            (activity as StartActivity).bottomNavigationVisibility(this)
        }


        }