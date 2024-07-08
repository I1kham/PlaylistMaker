package com.alchemtech.playlistmaker.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alchemtech.playlistmaker.databinding.ActivityMainBinding
import com.alchemtech.playlistmaker.presentation.ui.main.model.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSearch.setOnClickListener {
            viewModel.toSearch()
        }

        binding.buttonMediaLibrary.setOnClickListener {
            viewModel.toMediaLib()
        }

        binding.buttonSettings.setOnClickListener {
            viewModel.toSettings()
        }
    }
}