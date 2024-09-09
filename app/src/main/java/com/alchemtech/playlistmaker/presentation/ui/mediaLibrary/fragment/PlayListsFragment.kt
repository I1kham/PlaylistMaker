package com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.databinding.FragmentPlayListsBinding
import com.alchemtech.playlistmaker.domain.entity.PlayList
import com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.model.PlayListsViewModel
import com.alchemtech.playlistmaker.presentation.ui.playLikstCard.PlayListCardAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayListsFragment : Fragment() {

    private var binding: FragmentPlayListsBinding? = null
    private val favoriteTracksViewModel: PlayListsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPlayListsBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onResume() {
        super.onResume()



    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.addPlayListBut?.setOnClickListener {
            findNavController().navigate(R.id.action_mediaLibFragment_to_addPlayListFragment)
        }
                    val recyclerView = binding?.trackCardsRecyclerView

                    recyclerView?.layoutManager = GridLayoutManager(
                        view.context, /*Количество столбцов*/
                        2
                    ) //ориентация по умолчанию — вертикальная
                    recyclerView?.adapter = PlayListCardAdapter(
                        listOf(
                            PlayList(
                                "Play1",
                                "Description",
                                "content://media/picker/0/com.android.providers.media.photopicker/media/1000000034",
                            ),
                            PlayList(
                                "Play2",
                                "xfgnxvnxvbn",
                                "content://media/picker/0/com.android.providers.media.photopicker/media/1000000034",
                            )
                        )
                    )
                }

    override fun onDetach() {
        super.onDetach()
        binding = null
    }
}