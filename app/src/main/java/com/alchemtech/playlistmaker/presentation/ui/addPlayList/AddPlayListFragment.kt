package com.alchemtech.playlistmaker.presentation.ui.addPlayList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alchemtech.playlistmaker.databinding.MakePlayListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddPlayListFragment : Fragment() {

    private val viewModel: AddPlayListViewModel by viewModel()
    private var binding: MakePlayListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = MakePlayListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeRenderState()
        binding?.createBut?.isEnabled = false

    }

    override fun onDetach() {
        super.onDetach()
        binding = null
    }


    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }


    private fun observeRenderState() {
        val a = viewModel.observeRenderState()
        a.observe(getViewLifecycleOwner()) {
            render(it)
        }
    }

    private fun render(state: AddPlayListState) {
        when (state) {

            else -> {}
        }
    }
}