package com.alchemtech.playlistmaker.presentation.ui.mediaLibrary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alchemtech.playlistmaker.databinding.ActivityMediaLibraryBinding
import com.alchemtech.playlistmaker.presentation.ui.mediaLibrary.model.MediaLibViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MediaLibActivity : AppCompatActivity() {

    private val viewModel: MediaLibViewModel by viewModel()
    private lateinit var binding: ActivityMediaLibraryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMediaLibraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val back = binding.pageMediaLibPreview
        back.setOnClickListener {
            finish()
        }
    }
}