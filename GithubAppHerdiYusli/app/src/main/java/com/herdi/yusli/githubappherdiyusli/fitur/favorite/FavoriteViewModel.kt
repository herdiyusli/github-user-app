package com.herdi.yusli.githubappherdiyusli.fitur.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.herdi.yusli.githubappherdiyusli.data.database.FavUserRepository
import com.herdi.yusli.githubappherdiyusli.data.database.Favorite

class FavoriteViewModel(application : Application) : ViewModel() {
    private val mFavRepository : FavUserRepository = FavUserRepository(application)
    fun getAllFavorites() : LiveData<List<Favorite>> = mFavRepository.getAllFavorites()
}