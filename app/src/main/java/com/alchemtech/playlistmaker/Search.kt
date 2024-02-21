package com.alchemtech.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Search : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val back = findViewById<TextView>(R.id.search_text_View)
        back.setOnClickListener {
            val backIntent = Intent(this@Search,MainActivity ::class.java)
            startActivity(backIntent)
        }

    }
}