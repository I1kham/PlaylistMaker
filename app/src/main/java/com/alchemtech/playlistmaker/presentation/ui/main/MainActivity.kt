package com.alchemtech.playlistmaker.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alchemtech.playlistmaker.databinding.ActivityMainBinding
import com.alchemtech.playlistmaker.presentation.ui.main.model.MainViewModel
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by inject()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        viewModel = ViewModelProvider(
//            this,
//            MainViewModel.getViewModelFactory()
//        )[MainViewModel::class.java]


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