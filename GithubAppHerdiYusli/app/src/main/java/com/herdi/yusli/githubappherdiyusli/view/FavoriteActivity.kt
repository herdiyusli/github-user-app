package com.herdi.yusli.githubappherdiyusli.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.herdi.yusli.githubappherdiyusli.data.database.Favorite
import com.herdi.yusli.githubappherdiyusli.databinding.ActivityFavoriteBinding
import com.herdi.yusli.githubappherdiyusli.fitur.favorite.FavoriteViewModel
import com.herdi.yusli.githubappherdiyusli.fitur.favorite.FavoriteViewModelFactory
import com.herdi.yusli.githubappherdiyusli.fitur.favorite.ListFavAdapter


class FavoriteActivity : AppCompatActivity() {
    private lateinit var favoriteBinding: ActivityFavoriteBinding
    private lateinit var listFavAdapter: ListFavAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(favoriteBinding.root)

        supportActionBar?.title = "Favorite User"

        listFavAdapter = ListFavAdapter()
        listFavAdapter.notifyDataSetChanged()
        favoriteViewModel = viewModel(this@FavoriteActivity)

        listFavAdapter.setOnItemClickCallback(object : ListFavAdapter.OnItemClickCallback {
            override fun onItemClicked(favorite: Favorite) {
                Intent(this@FavoriteActivity, ProfileActivity::class.java).also {
                    it.putExtra(ProfileActivity.EXTRA_USERNAME, favorite.username)
                    it.putExtra(ProfileActivity.EXTRA_ID_USER_FAV, favorite.id)
                    it.putExtra(ProfileActivity.EXTRA_IMAGE_URL, favorite.img_url)
                    startActivity(it)
                }
            }

        })



        favoriteBinding.apply {
            favoriteRv.setHasFixedSize(true)
            favoriteRv.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                favoriteRv.layoutManager = GridLayoutManager(this@FavoriteActivity, 2)
            } else {
                favoriteRv.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            }
            favoriteRv.adapter = listFavAdapter
        }


        favoriteViewModel.getAllFavorites().observe(this, { favList ->
            if (favList!=null){
                listFavAdapter.setListFavorite(favList)
                favoriteBinding.favoriteRv.visibility = View.VISIBLE
                favoriteBinding.emptyFav.visibility = View.GONE
            }
            if (favList.isEmpty()) {
                favoriteBinding.favoriteRv.visibility = View.INVISIBLE
                favoriteBinding.emptyFav.visibility = View.VISIBLE
            }
        })

    }

    private fun viewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = FavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }


}