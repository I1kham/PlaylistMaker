package com.alchemtech.playlistmaker.presentation.ui.mediaLibrary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alchemtech.playlistmaker.databinding.ActivityMediaLibraryBinding
import com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.model.MediaLibViewModel

class MediaLibActivity : AppCompatActivity() {

    private lateinit var viewModel: MediaLibViewModel
    private lateinit var binding: ActivityMediaLibraryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            MediaLibViewModel.getViewModelFactory()
        )[MediaLibViewModel::class.java]

        binding = ActivityMediaLibraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val back = binding.mediaLibTextView
        back.setOnClickListener {
            finish()
        }
    }
}