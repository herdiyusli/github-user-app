package com.herdi.yusli.githubappherdiyusli.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.herdi.yusli.githubappherdiyusli.R
import com.herdi.yusli.githubappherdiyusli.databinding.ActivityProfileBinding
import com.herdi.yusli.githubappherdiyusli.fitur.detail.DetailUserViewModel
import com.herdi.yusli.githubappherdiyusli.fitur.detail.SectionPagerFollowAdapter

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileBinding: ActivityProfileBinding
    private lateinit var detailUserViewModel: DetailUserViewModel
    private var isCheckFavorite = false


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        profileBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(profileBinding.root)

        supportActionBar?.title = "Github Profile"

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id_user_fav = intent.getIntExtra(EXTRA_ID_USER_FAV, 0)
        val image_url = intent.getStringExtra(EXTRA_IMAGE_URL)

        val DataBundle = Bundle()
        DataBundle.putString(EXTRA_USERNAME, username)

        detailUserViewModel = ViewModelProvider(this)[DetailUserViewModel::class.java]
        detailUserViewModel.setUserDetailProfil(username!!)
        loading_detail(true)
        detailUserViewModel.getDetailUserProfile().observe(this, {
            if (it != null) {
                profileBinding.apply {
                    textViewName.text = it.nama_user
                    textViewUsername.text = it.username
                    textViewFollowers.text = "${it.jml_followers}"
                    textViewFollowing.text = "${it.jml_following}"
                    textViewKantor.text = it.kantor
                    textViewAlamat.text = it.alamat
                    textViewRepository.text = "${it.repository}"
                    Glide.with(this@ProfileActivity)
                        .load(it.img_user)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .circleCrop()
                        .into(ivFotoProfile)
                    loading_detail(false)
                }
            }
        })

        detailUserViewModel.check(id_user_fav)
            .observe(this, { listFav ->
                isCheckFavorite= listFav.isNotEmpty()

                if (listFav.isEmpty()) {
                    profileBinding.favAddButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                } else {
                    profileBinding.favAddButton.setImageResource(R.drawable.ic_baseline_favorite_full_24)
                }

            })

        profileBinding.favAddButton.apply {
            setOnClickListener {
                if (isCheckFavorite) {
                    detailUserViewModel.delete(id_user_fav)
                } else {
                    detailUserViewModel.insert(id_user_fav, username, image_url!!)
                }
            }
        }


        val sectionPagerFollowAdapter = SectionPagerFollowAdapter(this, DataBundle)
        val viewPagerFollow: ViewPager2 = profileBinding.vpager
        viewPagerFollow.adapter = sectionPagerFollowAdapter
        val tab_follow: TabLayout = profileBinding.tablayout
        TabLayoutMediator(tab_follow, viewPagerFollow) { tab, position ->
            tab.text = resources.getString(TITLES_TAB_LAYOUT[position])
        }.attach()
        supportActionBar?.elevation = 0f

    }

    private fun loading_detail(state: Boolean) {
        if (state) {
            profileBinding.apply {
                loadingBar.visibility = View.VISIBLE
                ivFotoProfile.visibility = View.INVISIBLE
                textViewUsername.visibility = View.INVISIBLE
                textViewName.visibility = View.INVISIBLE
                textViewFollowers.visibility = View.INVISIBLE
                textViewFollowing.visibility = View.INVISIBLE
                textViewRepository.visibility = View.INVISIBLE
                textViewKantor.visibility = View.INVISIBLE
                textViewAlamat.visibility = View.INVISIBLE
                favAddButton.visibility = View.INVISIBLE
                textView.visibility = View.INVISIBLE
                textView2.visibility = View.INVISIBLE
                textView3.visibility = View.INVISIBLE
            }
        } else {
            profileBinding.apply {
                loadingBar.visibility = View.GONE
                ivFotoProfile.visibility = View.VISIBLE
                textViewUsername.visibility = View.VISIBLE
                textViewName.visibility = View.VISIBLE
                textViewFollowers.visibility = View.VISIBLE
                textViewFollowing.visibility = View.VISIBLE
                textViewRepository.visibility = View.VISIBLE
                textViewKantor.visibility = View.VISIBLE
                textViewAlamat.visibility = View.VISIBLE
                favAddButton.visibility = View.VISIBLE
                textView.visibility = View.VISIBLE
                textView2.visibility = View.VISIBLE
                textView3.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings_activity -> {
                val intent = Intent(this, ThemeSettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> true
        }
    }

    companion object {
        @StringRes
        private val TITLES_TAB_LAYOUT = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID_USER_FAV = "extra_id_user_fav"
        const val EXTRA_IMAGE_URL = "extra_avatar_url"
    }

}