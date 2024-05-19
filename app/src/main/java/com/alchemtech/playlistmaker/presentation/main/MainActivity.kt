package com.alchemtech.playlistmaker.presentation.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.alchemtech.playlistmaker.R
import com.alchemtech.playlistmaker.presentation.mediaLibrary.MediaLibActivity
import com.alchemtech.playlistmaker.presentation.settings.SettingsActivity
import com.alchemtech.playlistmaker.presentation.tracks.TracksActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val searchBut = findViewById<Button>(R.id.buttonSearch)

        searchBut.setOnClickListener {
            val searchButIntent = Intent(this@MainActivity, TracksActivity::class.java)
            startActivity(searchButIntent)
        }


        val mediaLibBut = findViewById<Button>(R.id.buttonMediaLibrary)
        mediaLibBut.setOnClickListener {
            val mediaLibButIntent = Intent(this@MainActivity, MediaLibActivity::class.java)
            startActivity(mediaLibButIntent)
        }

        val settingsBut = findViewById<Button>(R.id.buttonSettings)
        settingsBut.setOnClickListener {
            val settingsButIntent = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(settingsButIntent)
        }
    }


}