package com.example.googlenews.ui.settings

import android.content.Intent
import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.googlenews.R
import com.example.googlenews.util.SHARED_KEY
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsActivity : AppCompatActivity() {
    private var switch: Switch? = null
    private val settingsViewModel by viewModel<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSavedState()
        setContentView(R.layout.activity_settings)
        switch = findViewById(R.id.settings_switch)
        saveBackgroundState()
    }

    private fun getSavedState() {
        if (settingsViewModel.getShared(SHARED_KEY)) {
            setTheme(R.style.DarkTheme)
            window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        } else {
            setTheme(R.style.AppTheme)
            window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        }
    }

    private fun saveBackgroundState() {
        if (settingsViewModel.getShared(SHARED_KEY)) {
            switch?.isChecked = true
            switch?.text = getString(R.string.enabled)
        } else {
            switch?.text = getString(R.string.disabled)
        }
        switch?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                settingsViewModel.saveToShared(SHARED_KEY, true)
                switch?.text = getString(R.string.enabled)
                window.statusBarColor = ContextCompat.getColor(this, R.color.white)
                restartApp()
            } else {
                settingsViewModel.saveToShared(SHARED_KEY, false)
                switch?.text = getString(R.string.disabled)
                window.statusBarColor = ContextCompat.getColor(this, R.color.black)
                restartApp()
            }
        }
    }

    private fun restartApp() {
        val restartIntent = baseContext.packageManager
            .getLaunchIntentForPackage(baseContext.packageName)
        restartIntent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        restartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        finish()
        startActivity(restartIntent)
    }
}
