package com.herdi.yusli.githubappherdiyusli.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.herdi.yusli.githubappherdiyusli.R
import com.herdi.yusli.githubappherdiyusli.data.response.User
import com.herdi.yusli.githubappherdiyusli.databinding.ActivityMainBinding
import com.herdi.yusli.githubappherdiyusli.fitur.search.ListUserAdapter
import com.herdi.yusli.githubappherdiyusli.fitur.search.UserViewModel
import com.herdi.yusli.githubappherdiyusli.fitur.settings.PreferencesThemeSettings
import com.herdi.yusli.githubappherdiyusli.fitur.settings.SettingsViewModelFactory
import com.herdi.yusli.githubappherdiyusli.fitur.settings.ThemeSettingsViewModel

class MainActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_settings")
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var listUserAdapter: ListUserAdapter


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val pref = PreferencesThemeSettings.getInstancePref(dataStore)
        val settingsViewModel = ViewModelProvider(
            this,
            SettingsViewModelFactory(pref)
        )[ThemeSettingsViewModel::class.java]
        settingsViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    activityMainBinding.apply {
                        SVUser.setBackgroundResource(R.drawable.searchview_background)
                    }

                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    activityMainBinding.apply {
                        SVUser.setBackgroundResource(R.drawable.searchview_background_orange)
                    }
                }
            })

        supportActionBar?.title = "Github Search"


        listUserAdapter = ListUserAdapter()
        listUserAdapter.notifyDataSetChanged()

        userViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[UserViewModel::class.java]

        listUserAdapter.setOnItemClick(object : ListUserAdapter.OnItemClick {
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, ProfileActivity::class.java).also {
                    it.putExtra(ProfileActivity.EXTRA_USERNAME, data.username)
                    it.putExtra(ProfileActivity.EXTRA_ID_USER_FAV, data.id)
                    it.putExtra(ProfileActivity.EXTRA_IMAGE_URL, data.img_user)
                    startActivity(it)
                }
            }

        })

        activityMainBinding.apply {
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                listUserRv.layoutManager = GridLayoutManager(this@MainActivity, 2)
            } else {
                listUserRv.layoutManager = LinearLayoutManager(this@MainActivity)
            }

            userViewModel.setSearchUsers("dicoding") //Preload Search Sesuai Review Pada Sub 2


            listUserRv.adapter = listUserAdapter


            activityMainBinding.SVUser.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchUsersGithub()
                    SVUser.clearFocus()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })

        }

        userViewModel.getSearchUsers().observe(this, {
            if (it.size >= 1) {
                activityMainBinding.apply {
                    listUserRv.visibility = View.VISIBLE
                    emptySearch.visibility = View.GONE
                }
                listUserAdapter.setListUser(it)
                loading(false)

            } else {
                activityMainBinding.apply {
                    listUserRv.visibility = View.INVISIBLE
                    emptySearch.visibility = View.VISIBLE
                }
                loading(false)
            }
        })

    }


    private fun loading(condition: Boolean) {
        if (condition) {
            activityMainBinding.apply {
                listUserRv.visibility = View.INVISIBLE
                emptySearch.visibility = View.GONE
                loading.visibility = View.VISIBLE
            }
        } else {
            activityMainBinding.loading.visibility = View.GONE
        }
    }


    private fun searchUsersGithub() {
        activityMainBinding.apply {
            val query = activityMainBinding.SVUser.query
            if (query.isEmpty()) return
            loading(true)
            userViewModel.setSearchUsers(query.toString())
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.actionbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings_activity -> {
                val intent = Intent(this, ThemeSettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.favorite_activity -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }

    private var backClick: Long = 0
    override fun onBackPressed() {
        if (backClick + 2500 > System.currentTimeMillis()) {
            super.onBackPressed()
            finish()
        } else {
            Toast.makeText(this, "Back sekali lagi untuk keluar", Toast.LENGTH_LONG).show()
        }
        backClick = System.currentTimeMillis()
    }

}