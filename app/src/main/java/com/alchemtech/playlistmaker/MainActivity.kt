package com.alchemtech.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val searchBut = findViewById<Button>(R.id.buttonSearch)

        searchBut.setOnClickListener {
            val searchButIntent = Intent(this@MainActivity, SearchActivity::class.java)
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