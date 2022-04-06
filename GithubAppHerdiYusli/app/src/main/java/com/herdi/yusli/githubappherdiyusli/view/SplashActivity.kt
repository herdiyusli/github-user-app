package com.herdi.yusli.githubappherdiyusli.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.herdi.yusli.githubappherdiyusli.databinding.ActivitySplashBinding
import com.herdi.yusli.githubappherdiyusli.fitur.settings.PreferencesThemeSettings
import com.herdi.yusli.githubappherdiyusli.fitur.settings.SettingsViewModelFactory
import com.herdi.yusli.githubappherdiyusli.fitur.settings.ThemeSettingsViewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var splashBinding: ActivitySplashBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_settings")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)
        supportActionBar?.hide()

        val pref = PreferencesThemeSettings.getInstancePref(dataStore)
        val settingsViewModel = ViewModelProvider(
            this,
            SettingsViewModelFactory(pref)
        )[ThemeSettingsViewModel::class.java]
        settingsViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    splashBinding.apply {
                        imageViewDark.visibility = View.VISIBLE
                        imageViewLight.visibility = View.INVISIBLE
                    }

                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    splashBinding.apply {
                        imageViewDark.visibility = View.INVISIBLE
                        imageViewLight.visibility = View.VISIBLE
                    }
                }
            })



        Handler(Looper.getMainLooper()).postDelayed({
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }, 1200)
    }
}