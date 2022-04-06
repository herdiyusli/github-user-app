package com.herdi.yusli.githubappherdiyusli.view

import android.content.Context
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.herdi.yusli.githubappherdiyusli.databinding.ActivityThemeSettingsBinding
import com.herdi.yusli.githubappherdiyusli.fitur.settings.PreferencesThemeSettings
import com.herdi.yusli.githubappherdiyusli.fitur.settings.SettingsViewModelFactory
import com.herdi.yusli.githubappherdiyusli.fitur.settings.ThemeSettingsViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_settings")

class ThemeSettingsActivity : AppCompatActivity() {

    private lateinit var themeSettingsBinding: ActivityThemeSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        themeSettingsBinding = ActivityThemeSettingsBinding.inflate(layoutInflater)
        setContentView(themeSettingsBinding.root)

        supportActionBar?.title = "Theme Settings"

        val buttonChangeTheme = themeSettingsBinding.switchTheme

        val pref = PreferencesThemeSettings.getInstancePref(dataStore)
        val settingsViewModel = ViewModelProvider(
            this,
            SettingsViewModelFactory(pref)
        )[ThemeSettingsViewModel::class.java]
        settingsViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    buttonChangeTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    buttonChangeTheme.isChecked = false
                }
            })

        buttonChangeTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingsViewModel.saveThemeSettings(isChecked)
        }
    }
}