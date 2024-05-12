package com.alchemtech.playlistmaker.presentation.settings

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDelegate.getDefaultNightMode
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import com.alchemtech.playlistmaker.presentation.themeSwitcher.DARK_THEME
import com.alchemtech.playlistmaker.R

class SettingsActivity : AppCompatActivity() {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)

        backButWork()

        toSupportButWork()

        shareAppButWork()

        termsOfUseButWork()

        darkThemeSwitchWork()

    }

    private fun darkThemeSwitchWork() {
        findViewById<Switch>(R.id.dayNightSwitch).setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setDefaultNightMode(MODE_NIGHT_YES)
            } else {
                setDefaultNightMode(MODE_NIGHT_NO)
            }
        }
        setThemeSwitcherChecked()
    }

    private fun termsOfUseButWork() {
        findViewById<Button>(R.id.buttonTermsOfUse).setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.linkTermsOfUse))))
        }
    }

    private fun shareAppButWork() {

        findViewById<Button>(R.id.buttonShareApp).setOnClickListener {
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
    }

    private fun toSupportButWork() {

        findViewById<Button>(R.id.buttonToSupport).setOnClickListener {
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
    }

    private fun backButWork() {
        findViewById<Button>(R.id.pageSettingsPreview).setOnClickListener {
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        getSharedPreferences(DARK_THEME.toString(), MODE_PRIVATE).edit()
            .putInt(DARK_THEME.toString(), getDefaultNightMode())
            .apply()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setDefaultNightMode(
            getSharedPreferences(
                DARK_THEME.toString(),
                MODE_PRIVATE
            ).getInt(DARK_THEME.toString(), 1)
        )
        setThemeSwitcherChecked()
    }

    private fun setThemeSwitcherChecked() {

        when (getSharedPreferences(DARK_THEME.toString(), MODE_PRIVATE).getInt(
            DARK_THEME.toString(),
            1
        )) {
            2-> {
                findViewById<Switch>(R.id.dayNightSwitch).isChecked = true
            }
            else -> findViewById<Switch>(R.id.dayNightSwitch).isChecked = false
        }
    }
}
