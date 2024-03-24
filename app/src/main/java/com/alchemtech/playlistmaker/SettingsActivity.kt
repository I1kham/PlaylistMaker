package com.alchemtech.playlistmaker

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate.*

class SettingsActivity : AppCompatActivity() {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
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
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, getString(R.string.buttonShareApp))
                type = "text/plain"
            }

            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, getString(R.string.buttonShareApp))
                type = "text/plain"
                startActivity(Intent.createChooser(this, getString(R.string.buttonShareAppTitle)))
            }

        }
        val buttonTermsOfUse = findViewById<Button>(R.id.buttonTermsOfUse)
        buttonTermsOfUse.setOnClickListener {

            val buttonTermsOfUseIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.linkTermsOfUse)))
            startActivity(buttonTermsOfUseIntent)

        }

        findViewById<Switch>(R.id.dayNightSwitch).setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setDefaultNightMode(MODE_NIGHT_YES)
            } else {
                setDefaultNightMode(MODE_NIGHT_NO)
            }
        }
    }

}
