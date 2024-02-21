package com.alchemtech.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val searchBut = findViewById<Button>(R.id.Search_but)

        searchBut.setOnClickListener {
            val searchButIntent = Intent(this@MainActivity,Search :: class.java )
            startActivity(searchButIntent)
        }


        val mediaLibBut = findViewById<Button>(R.id.media_library_button)
        mediaLibBut.setOnClickListener {
            val mediaLibButIntent = Intent(this@MainActivity, MediaLib::class.java)
            startActivity(mediaLibButIntent)
        }

        val settingsBut = findViewById<Button>(R.id.Settings_button)
        settingsBut.setOnClickListener {
            val settingsButIntent = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(settingsButIntent)
        }
    }


}