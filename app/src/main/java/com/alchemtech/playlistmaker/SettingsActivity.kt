package com.alchemtech.playlistmaker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backBut = findViewById<Button>(R.id.pageSettingsPreview)
        backBut.setOnClickListener {
            finish()
        }

        val buttonToSupport = findViewById<Button>(R.id.buttonToSupport)
        buttonToSupport.setOnClickListener {
            val buttonSupportIntent = Intent(Intent.ACTION_SENDTO)
            buttonSupportIntent.data = Uri.parse(getString(R.string.toSupportUri))
            buttonSupportIntent.putExtra(
                Intent.EXTRA_EMAIL,
                arrayOf(getString(R.string.supportMail))
            )
            buttonSupportIntent.putExtra(
                Intent.EXTRA_TEXT,
                getString(R.string.toSupportDefaultMail)
            )
            buttonSupportIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                getString(R.string.toSupportMailSubject)
            )
            startActivity(buttonSupportIntent)
        }

        val buttonShareApp = findViewById<Button>(R.id.buttonShareApp)
        buttonShareApp.setOnClickListener {
            val buttonShareAppIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, getString(R.string.buttonShareApp))
                type = "text/plain"
            }
            val shareIntent =
                Intent.createChooser(buttonShareAppIntent, getString(R.string.buttonShareAppTitle))
            startActivity(shareIntent)
        }
        val buttonTermsOfUse = findViewById<Button>(R.id.buttonTermsOfUse)
        buttonTermsOfUse.setOnClickListener {

            val buttonTermsOfUseIntent = Intent(Intent.ACTION_VIEW,Uri.parse(getString(R.string.linkTermsOfUse)))
            startActivity(buttonTermsOfUseIntent)

        }

    }


}

