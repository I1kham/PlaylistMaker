package com.alchemtech.playlistmaker.presentation.ui.mediaLibrary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alchemtech.playlistmaker.databinding.ActivityMediaLibraryBinding
import com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.model.MediaLibViewModel
import org.koin.android.ext.android.inject

class MediaLibActivity : AppCompatActivity() {

    private val viewModel: MediaLibViewModel by inject()
    private lateinit var binding: ActivityMediaLibraryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMediaLibraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val back = binding.mediaLibTextView
        back.setOnClickListener {
            finish()
        }
    }
}