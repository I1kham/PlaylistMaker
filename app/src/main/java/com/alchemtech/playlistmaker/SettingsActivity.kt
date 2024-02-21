package com.alchemtech.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backBut = findViewById<Button>(R.id.Settings_preview)
        backBut.setOnClickListener {
            val backButIntent = Intent(this,MainActivity ::class.java)
            startActivity(backButIntent)
        }


    }
}